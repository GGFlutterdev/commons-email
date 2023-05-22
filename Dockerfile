# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# syntax=docker/dockerfile:1

#We specified the maven version and jdk version that we want in our image
FROM maven:3.8.4-openjdk-17-slim AS build

#We created a directory
WORKDIR /app

#We copied the project in the image
COPY src ./src

#We copied the pom file in the image
COPY pom.xml ./

#We copied the conf directory in the image
COPY conf ./conf

#We copied the web application directory in the image
COPY serving-web-content ./serving-web-content

RUN export _JAVA_OPTIONS="-Djavax.net.debug=all"

#Run the commons-email pom
RUN mvn clean package

#Copy the dependency of web application
RUN mvn dependency:copy -f ./serving-web-content/pom.xml

#Run the web application pom
RUN mvn clean package -f ./serving-web-content/pom.xml

#We expose the port on which our web application will run
EXPOSE 8080

#We run the web application
CMD ["java","-jar","./serving-web-content/target/serving-web-content-0.0.1-SNAPSHOT.jar"]
