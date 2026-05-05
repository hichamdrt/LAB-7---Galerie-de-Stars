# 🌟 LAB 7 - Galerie de Stars

**Application Android de gestion et d’affichage d’une galerie de stars avec filtrage en temps réel**

---

## 📖 Table des matières
- [Introduction](#introduction)
- [Objectifs du laboratoire](#objectifs-du-laboratoire)
- [Fonctionnalités implémentées](#fonctionnalités-implémentées)
- [Captures d'écran](#captures-décran)
- [Architecture Technique](#architecture-technique)
- [Explication détaillée du filtrage](#explication-détaillée-du-filtrage)
- [Structure du projet](#structure-du-projet)
- [Installation et exécution](#installation-et-exécution)
- [Guide d'utilisation](#guide-dutilisation)
- [Défis rencontrés et solutions](#défis-rencontrés-et-solutions)
- [Compétences acquises](#compétences-acquises)
- [Améliorations futures possibles](#améliorations-futures-possibles)
- [Conclusion](#conclusion)
- [Auteur](#auteur)

---

## Introduction

Ce projet fait partie du **Laboratoire 7** du module Développement Mobile. Il consiste à développer une application Android permettant d’afficher une liste de stars (célébrités) dans une interface moderne utilisant `RecyclerView`.

L’objectif principal est d’implémenter un **système de recherche et de filtrage dynamique** en temps réel, en créant une classe personnalisée `NewFilter` qui hérite de la classe `Filter` d’Android.

---

## Objectifs du laboratoire

- Maîtriser l’utilisation de `RecyclerView` et des `Adapter`
- Comprendre le pattern **ViewHolder** pour optimiser les performances
- Apprendre à implémenter un filtrage personnalisé en étendant la classe `Filter`
- Gérer la mise à jour dynamique de la liste avec `notifyDataSetChanged()`
- Respecter les bonnes pratiques de développement Android (séparation des préoccupations, code propre)
- Préparer un projet versionné avec **Git** et **GitHub**

---

## Fonctionnalités implémentées

- Affichage d’une liste de stars avec photo, nom, date de naissance et bio
- Recherche en temps réel via `SearchView`
- Filtrage intelligent (par nom)
- Interface moderne avec `CardView`
- Design responsive
- Performance optimisée grâce au `ViewHolder`
- Gestion propre du filtrage via une classe interne `NewFilter`

---

## Captures d'écran

### 1. Interface Principale - Liste complète des Stars
<img width="250" height="368" alt="image" src="https://github.com/user-attachments/assets/1437dcbb-a228-4ef0-b33e-7bf63a5bfbdc" />


### 2. Démonstration du Filtrage en Temps Réel
<img width="249" height="201" alt="image" src="https://github.com/user-attachments/assets/e9e6b937-d691-4579-be3c-e3b18baa1d18" />


---

## Architecture Technique

L’application est construite autour de plusieurs composants clés :

- **`Star`** : Modèle de données (POJO) représentant une star.
- **`StarAdapter`** : Adapter personnalisé pour `RecyclerView` qui contient une classe interne `NewFilter`.
- **`NewFilter`** : Classe qui étend `Filter` pour gérer le filtrage côté client.
- **`ListActivity`** : Activité principale qui affiche la liste et gère la `SearchView`.
- Layouts XML : `activity_list.xml`, `item_star.xml`

Le filtrage est effectué **côté client** (pas via une base de données), ce qui est adapté pour un laboratoire pédagogique.

---

## Explication détaillée du filtrage

La classe `NewFilter` redéfinit deux méthodes importantes :

1. **`performFiltering(CharSequence constraint)`** : Effectue le filtrage selon le texte saisi par l’utilisateur.
2. **`publishResults(CharSequence constraint, FilterResults results)`** : Met à jour la liste filtrée et notifie l’adapter pour rafraîchir l’interface.

```java
@Override
protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
    starsFilter = (List<Star>) filterResults.values;
    mAdapter.notifyDataSetChanged();
}
