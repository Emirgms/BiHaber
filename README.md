# BiHaber | A Kotlin-powered News App

It is a news tracking application developed with Kotlin. MVVM Clean Architecture has been used, and it includes popular and up-to-date libraries.

The purpose of this repository is to serve as a guide for beginners.

## Preview

<img class="image-align-left" src="https://github.com/Emirgms/BiHaber/assets/55877628/4c45c9f7-d87a-4e31-9f6c-d3e068aeef33" width="400" height="900"> <img class="image-align-right" src="https://github.com/Emirgms/BiHaber/assets/55877628/3ab1a382-7f95-4682-91c5-f3091789c4f2" width="400" height="900">

 <img class="image-align-left" src="https://github.com/Emirgms/BiHaber/assets/55877628/543dd741-baa8-4882-9ae1-eefd230b52a6" width="400" height="900">  <img class="image-align-right" src="https://github.com/Emirgms/BiHaber/assets/55877628/b603a7f8-b233-43d1-a374-3e5a19d8439a" width="400" height="900">
  




# Project Folder Structure

Project Folder Structure | Description
:-------------------------------------:|:-------------------------------------:
![](https://github.com/Emirgms/BiHaber/assets/55877628/8d3ca847-08b8-48de-a0d8-68df8eb3a8e9) | The folder structure of the project is as shown on the left. The Clean Architecture approach has been used. Below are the explanations and details of the folders.

* adapter
* data
* db
* di
* local
* remote
* repository
* ui
* util

  #### ```App.kt``` file

This class extends the ```Application``` class and is annotated with the ```@HiltAndroidApp``` annotation.

## Dependencies

* Navigation
* Coroutines
* Retrofit
* GSON
* OkHttp
* Lifecycle [ViewModel - LiveData]
* Dagger & Hilt
* Glide
* Paging 3
* Room
