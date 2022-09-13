<template>
  <div>
    <div class="wrapper">
      <svg :viewBox="`0 0 100 ${viewPortHeight}`">
        <line v-for="line in [viewPortHeight*(3/4), viewPortHeight*(2/4), viewPortHeight*(1/4)]"
            x1="0" x2="100" :y1="line" :y2="line"></line>

<!--        <line x1="0" x2="100" y1="25" y2="25"></line>-->

<!--        <line x1="0" x2="100" y1="15" y2="15"></line>-->
<!--        <line x1="0" x2="100" y1="5" y2="5"></line>-->

        <path :d="averages" class="averages"></path>
        <path :d="commands"></path>

          <circle
              v-for="line in getLines"
              v-bind:key="line"
              tabindex="0"
              :aria-label="`preview ${line.name}`"
              role="button"

              @click="$emit('showOverlay', line)"
              @keydown="(e) => { if (e.keyCode === 13) {this.enterPressed(e, line);}}"
              @focusin="(e) => e.target.setAttribute('r', circleSize * 1.5)"
              @focusout="(e) => e.target.setAttribute('r', circleSize)"
              @mouseover="(e) => e.target.setAttribute('r', circleSize * 1.5)"
              @mouseleave="(e) => e.target.setAttribute('r', circleSize)"

              data-betsoc-degree-of-assessment="APPRENTICE"
              :r="circleSize"
              :cx="line.x"
              :cy="line.y"
          ></circle>
      </svg>
    </div>
  </div>
</template>

<script>
export default {
  name: "SkillDiagram",
  props: {
    measurementPoints: Array,
  },

  data() {
    return {
      dataPoints: [{y:25}, {y:25}, {y:5}, {y:25}, {y:25}, {y:25}, {y:10}, {y:15}, {y:5}, {y:20}, {y:5}],
      chunkSize: 3,
      circleSize: 1.5,
      viewPortHeight: 20,
    }
  },
  computed: {
    scale() {
      return Math.abs(100 / (this.measurementPoints.length-1));
    },
    commands() {
      return this.getLinesMethod().reduce((acc, point, i, a) => i === 0
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
    getLines() {
      return this.getLinesMethod();
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
    controlPoint(current, previous, next, reverse) {
      // When 'current' is the first or last point of the array
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
    getLinesMethod() {
      return this.measurementPoints.map((point, i) => {
        point.x = i * this.scale;
        point.y = this.measurementToNumber(point.progress);
        return point;
      })
    },
    getWeekAverages() {
      return this.weekAverages().map((y, i) => {
        return {x: i * this.scale * this.chunkSize, y}
      })
    },
    weekAverages() {
      const lines = this.getLinesMethod();
      const averages = [];
      for (let i = 0; i < lines.length; i+=this.chunkSize) {
        averages.push(lines.slice(i,  i + this.chunkSize).map((e) => e.y).reduce((a, b) => a + b) / lines.slice(i, i + this.chunkSize).length);
      }
      return averages;
    },
    measurementToNumber(measurement) {
      switch (measurement) {
        case "IN_ONTWIKKELING": return this.viewPortHeight * (3/4);
        case "OP_NIVEAU": return this.viewPortHeight * (2/4);
        case "BOVEN_NIVEAU": return this.viewPortHeight * (1/4);
      }
    },
    enterPressed(e, line) {
      e.preventDefault();
      e.stopImmediatePropagation();
      //Do your stuff...
      this.$emit('showOverlay', line)
    },
  },
  emits: ['showOverlay']
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
  g {
    &:focus, &:focus-within {

      fill: yellow;
    }
  }
  circle {
    transition: r .15s ease-in-out, fill .2s ease-in-out;

    // PEER,
    // APPRENTICE,
    // GUILD,
    // GUILD_MASTER,
    // EXTERN
    &[data-betsoc-degree-of-assessment="PEER"] {
      fill: steelblue;

      &:hover, &:focus {
        fill: darken(steelblue, 5%);
      }
    }

    &[data-betsoc-degree-of-assessment="APPRENTICE"] {
      fill: darkorange;

      &:hover, &:focus {
        fill: darken(darkorange, 5%);
      }
    }

    &[data-betsoc-degree-of-assessment="GUILD"] {
      fill: goldenrod;

      &:hover, &:focus {
        fill: darken(goldenrod, 5%);
      }
    }

    &[data-betsoc-degree-of-assessment="GUILD_MASTER"] {
      fill: tomato;

      &:hover, &:focus {
        fill: darken(tomato, 5%);
      }
    }

    &[data-betsoc-degree-of-assessment="EXTERN"] {
      fill: teal;

      &:hover, &:focus {
        fill: darken(teal, 5%);
      }
    }

    //&:hover, &:focus {
    //  fill: #42b983;
    //}
  }
}
//.wrapper {
//  width: 90vw;
//}
.averages {
  stroke: gray;
}
</style>