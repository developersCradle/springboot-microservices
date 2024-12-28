# Build the Angular application
FROM node:16 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular application
RUN npm run build --prod

# Serve the application using Nginx
FROM nginx:alpine

# Remove default Nginx index page
RUN rm /usr/share/nginx/html/*

# Copy the built Angular app from the previous stage
COPY --from=build /app/dist/country-service-front /usr/share/nginx/html

# Expose the port that Nginx will run on
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]