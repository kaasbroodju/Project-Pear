import {defineConfig} from 'vite';
import vue from '@vitejs/plugin-vue';
import {VitePWA} from "vite-plugin-pwa";

const path = require("path");
export default defineConfig({
    plugins: [
        vue(),
        VitePWA({
            mode: "development",
            base: "/",
            srcDir: "src",
            filename: "sw.js",
            includeAssets: ["/favicon.png"],
            strategies: "injectManifest",
            manifest: {
                name: "Project Pear",
                short_name: "Pear",
                theme_color: "#2f3136",
                start_url: "/",
                display: "standalone",
                display_override: ["fullscreen", "tabbed"],
                background_color: "#37393e",
                icons: [{
                    src: "img/icons/pwa-192x192.png",
                    sizes: "192x192",
                    type: "image/png"
                },
                {
                    src: "img/icons/pwa-512x512.png",
                    sizes: "512x512",
                    type: "image/png"
                },
                {
                    src: "img/icons/apple-touch-icon.png",
                    sizes: "180x180",
                    type: "image/png"
                },
                {
                    src: "img/icons/pwa-512x512.png",
                    sizes: "512x512",
                    type: "image/png",
                    purpose: "any maskable"
                }],
            },
        })
    ],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src"),
        },
    },
    server: {
        port: 8080,
        proxy: {
            '^/api': {
                target: 'http://localhost:3080',
                changeOrigin: true,
            },
        }
    }

})