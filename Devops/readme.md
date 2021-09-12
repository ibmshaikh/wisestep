#How to run this application locally

### Prerequisite
1. Docker Engine
2. Docker Compose

I have uploaded backend and frontend artifact in Dockerhub so no need to build the image.
You can find Dockerfile of Backend and Frontend in their respective folder if you want.


Just copy docker-compose.yaml in your computer, and open the file and replace SMTP values with your value. and run below command

`docker-compose up`

`docker-compose up -d` in demon mode

After running above command boom the application will up and running with all the dependency.

**What images conatin in docker-compose**
1. Forntend
2. Backend
3. MongoDB
4. Redis