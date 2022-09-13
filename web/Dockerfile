# build stage
FROM node:lts-alpine as build-stage
WORKDIR /pear
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY --from=build-stage /pear/dist /var/www/html/
COPY ./nginx/project_pear.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]