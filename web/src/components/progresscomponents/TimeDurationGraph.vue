<template>
    <svg viewBox="0 0 100 100" width="50vw">
      <line x1="0" :x2="dataPoints.length > totalDays ? 100 * this.scaleX : 100" y1="100" y2="25"></line>
      <text x="0" y="24.5" font-size=".25em">{{ this.targetTime/ (60 * 60 * 1000) }} uur</text>
      <line
          style="stroke-dasharray: 6 2"
          x1="0"
          x2="100"
          y1="25"
          y2="25"
      />
      <template v-if="dataPoints.length > 0">
        <line
            :x1="dataPoints[dataPoints.length-1].x + ((dataPoints.length > (this.totalDays / 2) ? -1 : 1) * 17.5)"
            :x2="dataPoints[dataPoints.length-1].x"
            :y1="dataPoints[dataPoints.length-1].y"
            :y2="dataPoints[dataPoints.length-1].y"
        />
        <text
            :x="dataPoints[dataPoints.length-1].x + ((dataPoints.length > (this.totalDays / 2) ? -1 : 1) * 17.5)"
            :y="dataPoints[dataPoints.length-1].y-0.5"
            font-size=".25em"
            :text-anchor="dataPoints.length > (this.totalDays / 2) ? 'start' : 'end'"
        >{{ Math.round(this.achievedTime/ (60 * 60 * 1000)) }}~ uur</text>
      </template>
      <line v-if="dataPoints.length > totalDays"
            :x1="100 * this.scaleX"
            x2="100"
            y1="25"
            y2="25"
      />
      <path :d="commands"/>

    </svg>

</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "TimeDurationGraph",
  props: {
    studentEmail: {
      type: String,
      required: false,
      default: undefined,
    }
  },
  data() {
    return {
      targetTime: 720 * (60*60*1000),
      totalDays: 100,
      dataPoints: [],
      scaleX: 1,
      achievedTime: 0
    }
  },
  mounted() {
    const promise = this.studentEmail ? fetchAuth(`/api/teacher/student/${this.studentEmail}/time`) : fetchAuth("/api/progress/time")
    promise.then(response => response.json()).then(timeData => {
      this.achievedTime = timeData.reduce((acc, e) => acc + e.duration, 0);
      // if (this.achievedTime < this.targetTime) this.achievedTime = this.targetTime;


      const dataPoints = [];
      const total = this.targetTime * 1.25;
      this.dataPoints = Array(timeData.length)
      let remainingTime = total
      this.scaleX = 100 / (this.dataPoints.length > this.totalDays ? this.dataPoints.length : this.totalDays);
      for (let i = 0; i < timeData.length; i++) {
        remainingTime -= timeData[i].duration;

        dataPoints.push({x: i * this.scaleX, y:100*remainingTime/total});
      }


      this.dataPoints = dataPoints;

    })
  },
  computed: {
    commands() {
      return this.dataPoints.reduce((acc, point, i, a) => i === 0
              ? `M ${point.x},${point.y}`
              : `${acc} ${this.bezierCommand(point, i, a)}`
          , '');
    },
    averages() {
      return this.getWeekAverages().reduce((acc, point, i, a) => i === 0
              ? `M ${point.x},${point.y}`
              : `${acc} ${this.bezierCommand(point, i, a)}`
          , '');
    },
  },
  methods: {
    line(a, b) {
      const lengthX = b.x - a.x;
      const lengthY = b.y - a.y;

      return {
        length: Math.sqrt(Math.pow(lengthX, 2) + Math.pow(lengthY, 2)),
        angle: Math.atan2(lengthY, lengthX)
      }
    },
    controlPoint(current, previous, next, reverse) {  // When 'current' is the first or last point of the array
      // 'previous' or 'next' don't exist.
      // Replace with 'current'
      const p = previous || current
      const n = next || current  // The smoothing ratio
      const smoothing = 0.2  // Properties of the opposed-line
      const o = this.line(p, n)  // If is end-control-point, add PI to the angle to go backward
      const angle = o.angle + (reverse ? Math.PI : 0)
      const length = o.length * smoothing  // The control point position is relative to the current point
      const x = current.x + Math.cos(angle) * length
      const y = current.y + Math.sin(angle) * length;
      return {x, y}
    },
    bezierCommand(point, i, a) {  // start control point
      const cps = this.controlPoint(a[i - 1], a[i - 2], point)  // end control point
      const cpe = this.controlPoint(point, a[i - 1], a[i + 1], true)

      return `C ${cps.x},${cps.y} ${cpe.x},${cpe.y} ${point.x},${point.y}`
    },
    // getLines() {
    //   return this.dataPoints.map((point, i) => {
    //
    //     return {x: i * this.scaleX, y: point.y}
    //   })
    // },
    // getWeekAverages() {
    //   return this.weekAverages().map((y, i) => {
    //     return {x: i * this.scaleX * 5, y}
    //   })
    // },
    // weekAverages() {
    //   const lines = this.getLines();
    //   const averages = [];
    //   let i = 0
    //
    //   for (; i < lines.length; i+=5) {
    //
    //     averages.push(lines.slice(i,  i + 5).map((e) => e.y).reduce((a, b) => a + b) / lines.slice(i, i + 5).length);
    //   }
    //   i *= 5
    //   for (; i < lines.length; i++) {
    //
    //   }
    //
    //
    //   return averages;
    // }
  }
}
</script>

<style scoped lang="less">
svg {
  background-color: lightblue;

  > path {
    stroke: black;
    stroke-width: .05em;
    fill: none;
  }

  > line {
    stroke: darkgray;
    stroke-width: .025em;
  }
}
</style>