import {precacheAndRoute} from "workbox-precaching";

self.addEventListener("message", (event) => {
    if (event.data && event.data.type === "SKIP_WAITING") self.skipWaiting();
});
// self.__WB_MANIFEST is default injection point
precacheAndRoute(self.__WB_MANIFEST);

// self.addEventListener("fetch", function (event) {
//     console.log("hello from service worker",
//         self
//     )
//     // localStorage
//     // event.request.headers.append('x-pear-temp', 'hello world')
//     return fetch(new Request(event.request, {
//         headers: {
//             ...event.request.headers,
//             'x-pear-temp': 'hello world'
//         }
//     }));
// })
//     event.respondWith( async function() {
//     const cachedResponse = await caches.match(event.request);
//
//     // Generate partial responses
//     // This doesn't seem to be needed anymore
//     /*if (cachedResponse && event.request.headers.has('range') && cachedResponse.status !== 206) {
//       // Create a partial response.
//       // At some point we'll fix caches.match to generate these.
//       const blob = await cachedResponse.blob();
//       const rangeResult = /bytes=(\d+)-(\d*)/.exec(event.request.headers.get('range'));
//       const rangeStart = Number(rangeResult[1]);
//       const rangeEnd = Number(rangeResult[2]) || blob.size - 1;
//
//       const headers = new Headers(cachedResponse.headers);
//       headers.set('Content-Range', `bytes ${rangeStart}-${rangeEnd}/${blob.size}`);
//       headers.set('Content-Length', (rangeEnd - rangeStart) + 1);
//       const body = blob.slice(rangeStart, rangeEnd + 1);
//
//       return new Response(body, { headers, status: 206 });
//     }*/
//
//     return cachedResponse || fetch(event.request);
//     })
// });
//
// addEventListener('install', (event) => {
//     self.skipWaiting();
//
//     event.waitUntil(async function() {
//         const cache = await caches.open('dynamic');
//         await cache.addAll([
//             "/api/settings/user/picture",
//         ]);
//     }());
// });