import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import {setRouterInstance} from "@/utils/authrequest";
import {createPinia} from "pinia/dist/pinia";

createApp(App).use(router).use(createPinia()).mount('#app')

setRouterInstance(router)
