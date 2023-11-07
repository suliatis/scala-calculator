default: help

clean: ## Clean the build files
	scala-cli --power clean .
	rm -rf .bsp
	rm -rf .metals
	rm App.js
	rm App.js.map

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

help: ## Show help message
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' -e 's/:.*#/: #/' | column -t -s '##'
