export const formDataToJsonString = (formData) => {
    let obj = {};
    for (const key of formData.keys()) {
        obj[key] = formData.get(key);
    }
    return JSON.stringify(obj);
}