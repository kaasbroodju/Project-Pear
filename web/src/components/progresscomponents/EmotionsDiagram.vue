<template>

      <svg viewBox="0 0 100 30">
        <line x1="0" x2="100" y1="25" y2="25"></line>

        <line x1="0" x2="100" y1="20" y2="20"></line>
        <line x1="0" x2="100" y1="15" y2="15"></line>
        <line x1="0" x2="100" y1="10" y2="10"></line>
        <line x1="0" x2="100" y1="5" y2="5"></line>

<!--        <path :d="averages"></path>-->
        <path :d="commands"></path>

      </svg>

</template>

<script>
import fetchAuth from "@/utils/authrequest";

export default {
  name: "EmotionsDiagram",
  props: {
    studentEmail: {
      type: String,
      required: false,
      default: undefined,
    }
  },
  data() {
    return {
      dataPoints: [],
    }
  },
  mounted() {
    if (this.studentEmail) {
      fetchAuth(`/api/teacher/student/${this.studentEmail}/emotion`).then(response => response.json()).then(timeData => {
        this.dataPoints = timeData.map(e => {return {y:this.translateValue(e.emotion)}});
      })
    } else {
      fetchAuth("/api/progress/emotion").then(response => response.json()).then(timeData => {
        this.dataPoints = timeData.map(e => {return {y:this.translateValue(e.emotion)}});
      })
    }

  },
  computed: {
    scale() {
      return 100 / (this.dataPoints.length-1);
    },
    commands() {
      return this.getLines().reduce((acc, point, i, a) => i === 0
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
    translateValue(value) {
      switch(value) {
        case "ZEER_GOED": return 5;
        case "GOED": return 10;
        case "NEUTRAAL": return 15;
        case "SLECHT": return 20;
        case "ZEER_SLECHT": return 25;
        default: return 25
      }
    },
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
      const smoothing = 0.1  // Properties of the opposed-line
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
    getLines() {
      return this.dataPoints.map((point, i) => {
        return {x: i * this.scale, y: point.y}
      })
    },
    getWeekAverages() {
      return this.weekAverages().map((y, i) => {
        return {x: i * this.scale * 5, y}
      })
    },
    weekAverages() {
      const lines = this.getLines();
      const averages = [];
      let i = 0
      for (; i < lines.length; i+=5) {
        averages.push(lines.slice(i,  i + 5).map((e) => e.y).reduce((a, b) => a + b) / lines.slice(i, i + 5).length);
      }
      i *= 5
      for (; i < lines.length; i++) {
      }

      return averages;
    }
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
  .wrapper {
    width: 50vw;
    height: 50vh;
  }
  .averages {
    stroke: gray;
  }
</style>