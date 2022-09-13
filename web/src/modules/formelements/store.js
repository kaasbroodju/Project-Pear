import {defineStore} from "pinia/dist/pinia";


export const selfPickableStore = defineStore('selfPickableStore', {
    state: () => ({ dataPointTypesAlreadyInUse: [] }),
    actions: {
        add(dataPointType) {
            this.dataPointTypesAlreadyInUse.push(dataPointType)
        },
        remove(dataPointType) {
            this.dataPointTypesAlreadyInUse = this.dataPointTypesAlreadyInUse.filter(type => type !== dataPointType)
        },
        clear() {
            this.dataPointTypesAlreadyInUse = [];
        }
    }
})