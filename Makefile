default: help

clean: ## Clean the build files
	scala-cli --power clean .
	rm -rf .bsp
	rm -rf .metals
	rm App.js
	rm App.js.map
	rm _site

repl: ## Start a repl including the project
	scala-cli repl .

ide: ## Generate ide configuration files
	scala-cli setup-ide .

live: ## Package the application in dev mode
	scala-cli --power package .\
		--watch\
		--js-mode dev\
		--js-emit-source-maps\
		--output App.js -f

release: ## Package the application in release mode
	scala-cli --power package .\
		--js-mode release\
		--output App.js -f

site: ## Collect static assets for GitHub Pages
	mkdir _site
	cp -R styles _site/styles
	cp App.js _site/App.js
	cp index.html _site/index.html
	chmod -c -R +rX "_site/"

test: ## Run tests
	scala-cli test .

help: ## Show help message
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' -e 's/:.*#/: #/' | column -t -s '##'
