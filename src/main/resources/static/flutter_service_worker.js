'use strict';
const CACHE_NAME = 'flutter-app-cache';
const RESOURCES = {
  "assets/AssetManifest.json": "f991983e4b74ce3d25e0a253740ca595",
"assets/assets/images/logo.png": "f7f31fec3f5c4d27740a1030b108cd90",
"assets/FontManifest.json": "4804ffd24bfc476aea8ec732f98d72ad",
"assets/fonts/GoogleSans-Regular.ttf": "a1bf9d6baa97ff8cb0a0f780e9f1fbe6",
"assets/fonts/MaterialIcons-Regular.ttf": "56d3ffdef7a25659eab6a68a3fbfaf16",
"assets/images/logo.png": "f7f31fec3f5c4d27740a1030b108cd90",
"assets/LICENSE": "dd1af94b01d7be64f26b6998fd8b4418",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "115e937bb829a890521f72d2e664b632",
"favicon.png": "b99fdfb6511ae375b41bbdd567a62106",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"index.html": "4b898804a6b3087b8e94aa893cc0f9d0",
"/": "4b898804a6b3087b8e94aa893cc0f9d0",
"main.dart.js": "4a1f013b8515e33eb86c711776f03d7c",
"manifest.json": "099f042d36f8dbf5b292ba951263a941"
};

self.addEventListener('activate', function (event) {
  event.waitUntil(
    caches.keys().then(function (cacheName) {
      return caches.delete(cacheName);
    }).then(function (_) {
      return caches.open(CACHE_NAME);
    }).then(function (cache) {
      return cache.addAll(Object.keys(RESOURCES));
    })
  );
});

self.addEventListener('fetch', function (event) {
  event.respondWith(
    caches.match(event.request)
      .then(function (response) {
        if (response) {
          return response;
        }
        return fetch(event.request);
      })
  );
});
