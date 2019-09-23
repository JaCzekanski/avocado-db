all:
	docker run -it -v $(CURDIR):/app gradle:jdk12 /bin/sh -c "cd /app && gradle --no-daemon shadowJar"
