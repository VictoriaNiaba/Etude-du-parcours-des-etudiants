
# Hello there
## 0) Avant tout
    npm install    
## 1) Infos
Ce projet est d'une version d'angular particulière (la 11).
Si vous êtes en version 10, merci de suivre ce tuto :

    npm uninstall -g @angular/cli
    npm cache clean --force
    npm install -g @angular/cli
    npm install
    npm install --save-dev @angular/cli@latest

## 2) Les tests
Merci de vous créer une branche test !
## 3) Qu'est-ce qui est actif dans ce dépôt ?
J'ai implémenté par défaut :
* Un style
	* Font awesome (free)
	* Bootstrap avec le thème [cerulean](https://bootswatch.com/cerulean/)
* Une librairie d’affinage de node [ngx-echarts](https://xieziyu.github.io/ngx-echarts/#/welcome)
* Un système d'authentification
## 4) Notes
Si votre projet est en version 10 et que vous souhaitez passer en 11 voici les lignes que j'ai entées après la partie 1) :

    npm install --save-dev @angular/cli@latest
    npm i 
    ng update @angular/cli 
    ng update @angular/core
    npm install --save-dev @angular-devkit/build-angular

Voici la documentation de ngx-echarts : [ICI](https://xieziyu.github.io/ngx-echarts/api-doc/)
