all:
	docker run -it -v $(CURDIR):/app node:lts-alpine /bin/sh -c "cd /app && npm install && npm run build"

clean:
	rm -rf node_modules/
