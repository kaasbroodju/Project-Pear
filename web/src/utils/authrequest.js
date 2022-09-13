var routerInstance = undefined;

export function setRouterInstance(instance) {
    routerInstance = instance
}

function fetchAuth(requestURL, options = {}) {
    if (!options.headers) options.headers = {};
    options.headers['Authorization'] = localStorage.getItem("accessToken")
    return fetch(requestURL, options)
        .then(response => {
            if (response.status === 403 || response.status === 401) {
                throw ""; // token invalid or needs a refresh
            } else {
                return response
            }
        })
        .catch(async () => {
            
            const ok = await fetch("/api/token/refresh", {headers: {Authorization: localStorage.getItem("refreshToken")}})
                .then(refreshResponse => {
                    if (refreshResponse.ok) {
                        return refreshResponse.json();
                    }
                    return false;
                })
                .then((value) => {
                    if (typeof value == "boolean") {
                        return value
                    } else {
                        localStorage.setItem("accessToken", "Bearer " + value.accessToken);
                        localStorage.setItem("refreshToken", "Bearer " + value.refreshToken);
                        return true;
                    }
                })
            if (ok) {
                options.headers['Authorization'] = localStorage.getItem("accessToken")
                return fetch(requestURL, options)
            } else {
                throw "";
            }
        })
        .catch(() => {
            
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");

            if (routerInstance) {
                window.showLoginOverlay();
                // routerInstance.push({path: "/login"})
            }
            // show login overlay
            // throw
        })
}

export default fetchAuth