# Welcome to Archinaut Web Application!

This is the web aplication for **Archinaut**. With this project you can visualize the evolution of the architecture an its tendencies based on the results from Archinaut Github Action.
Archinaut Web Application is a Spring application made with Spring initializer and its frameworks, also it is used Chart JS.

## How to use it
It is easy to use. Once you have added the Github Action of Archinaut at your pipeline and downloaded the results you have to follow the next steps.

### Download the MongoDB image from Dockerhub

After you download the mongoDb image you have to edit its application.yml(If you have Docker Desktop and VS Code in the image appears the option to open it with VS Code and it opens in the application.yml file automatically).

You have to write the next in the file:
```javascript
spring:
	profiles:
		active: development
logging:
	level:
		root: INFO
		org.springframework.*: INFO
---
server:
	port: 8080
---
spring:
	data:
		mongodb:
			port: 27017
			host: localhost
			database: ArchinautAnalysis
			username: dbAdmin
			password: admin
```


### Download Archinaut Web Aplication

Once you have download the project run it as Spring aplication and enter to http://localhost:8080/.
The application.yml file is already configurated to conect automatically with the mongoDb image that was previously configured. In the same application.yml you can configure other things as ports or the mongo configuration in case that you have configurated in another way the mongoDb image

### Once it is running

It has an intuitive interface that make it easy to use.
The only one thing that can be hard is uploading a new report from a project, to do it follow the next steps.
- Click on the "Upload a new report" button. It is going to open a new window with the Swagger interface, here you can test the endpoints.
- Go to reports controller
- Choose the "Post option"(the green one)
- Click on "Try it out", then you can use the text editor
- Copy the report you want to upload and paste it on the editor
- Click on "Excecute"
- Done, Look for it in the "Project Selection". 

Note: Archinaut changes the project name slash for a dash. Example: ErickESC/archinautWebApplication -> ErickESC-archinautWebApplication
