<template>
  <label class="container" :disabled="disable">
    <input
        type="radio"
        name="emotion"
        data-betsoc-label="emotion"
        :required="required"
        :disabled="disable"
        :value="emotion"
    >
    <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg" style="fill: white;" width="6em">
      <g class="smiley">
        <g :class="emotion">
          <g>
            <circle cx="128" cy="128" r="120"/>
            <circle cx="96" cy="64" r="16" class="eyes"/>
            <rect x="72" y="64" width="48" height="32"/>
            <circle cx="96" cy="96" r="16" class="eyes"/>
            <circle cx="160" cy="64" r="16" class="eyes"/>
            <rect x="136" y="64" width="48" height="32" />
            <circle cx="160" cy="96" r="16" class="eyes"/>
          </g>
          <g>
            <circle cx="64" cy="160" r="16" class="eyes"/>
            <circle cx="192" cy="160" r="16" class="eyes"/>
            <path :d="`M 64 160 Q 128 `+ positionTop + `, 192 160`" class="mouth"/>
          </g>
        </g>
      </g>
    </svg>
  </label>
</template>

<script>
export default {
  name: "EmotionRadioInput",
  props: {
    emotion: String,
    disable: Boolean,
    required: Boolean,
  },
  data() {
    return {
      active: false,
    }
  },
  computed: {
    positionTop: function () {
      switch (this.emotion) {
        case "ZEER_GOED": return 224;
        case "GOED": return 192;
        case "NEUTRAAL": return 160;
        case "SLECHT": return 128;
        case "ZEER_SLECHT": return 96;
        default: return 160;
      }
    }
  }
}
</script>

<style scoped lang="less">

label  {
  display: inline-block;
}

@hoverColor: color;
@bigSad: red;
@sad: darkorange;
@neutraal: gold;
@blij: palegreen;
@zeerBlij: limegreen;
/* Customize the label (the container) */
.container {

  &[disabled=true] {
    cursor: unset !important;
  }

  &[disabled=false] {
    cursor: pointer;
  }
  //cursor: pointer;
  user-select: none;
  padding: 1em;
  font-size: unset;

  input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
    height: 0;
    width: 0;
  }

  //input:checked ~ svg g.smiley, input&:not(:disabled) &:hover svg g.smiley {

  input:checked ~ svg g.smiley, &[disabled=false]:hover svg g.smiley, &[disabled=false] input:focus ~ svg g.smiley {
    .ZEER_SLECHT {
      @hoverColor: @bigSad;
      circle {
        stroke: @hoverColor;
      }
      .mouth {
        stroke: @hoverColor;
      }
      .eyes {
        fill: @hoverColor;
        stroke: @hoverColor;
      }
      rect {
        fill: @hoverColor;
      }
    }

    .SLECHT {
      @hoverColor: @sad;
      circle {
        stroke: @hoverColor;
      }
      .mouth {
        stroke: @hoverColor;
      }
      .eyes {
        fill: @hoverColor;
        stroke: @hoverColor;
      }
      rect {
        fill: @hoverColor;
      }
    }
    .NEUTRAAL {
       @hoverColor: @neutraal;
       circle {
         stroke: @hoverColor;
       }
       .mouth {
         stroke: @hoverColor;
       }
       .eyes {
         fill: @hoverColor;
         stroke: @hoverColor;
       }
       rect {
         fill: @hoverColor;
       }
     }
    .GOED {
      @hoverColor: @blij;
      circle {
        stroke: @hoverColor;
      }
      .mouth {
        stroke: @hoverColor;
      }
      .eyes {
        fill: @hoverColor;
        stroke: @hoverColor;
      }
      rect {
        fill: @hoverColor;
      }
    }
    .ZEER_GOED {
      @hoverColor: @zeerBlij;
      circle {
        stroke: @hoverColor;
      }
      .mouth {
        stroke: @hoverColor;
      }
      .eyes {
        fill: @hoverColor;
        stroke: @hoverColor;
      }
      rect {
        fill: @hoverColor;
      }
    }

  }
}



.smiley {
  @color: #808080FF;

  circle {
    fill: none;
    stroke-width: 16;
    stroke: @color;
  }
  .mouth {
    fill: none;
    stroke: @color;
    stroke-width: 48;
  }
  .eyes {
    fill: @color;
  }
  rect {
    fill: @color;
  }

}

</style>