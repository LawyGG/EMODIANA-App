# EMODIANA-App
Interactive instrument designed for subjetive emotional evaluation on children

##Index
* Introduction
* How it works
* Data storage/sending

## Introduction
This repo contains the code for the EMODIANA app, a project developing an interactive intrument suitable to measure UX in children. This apporach is based on emotions, the ones included are: Happiness, Fun, Sadness, Fear, Surprise, Pride, Angriness, Disgust, Neutrality and Shame.
The instrument is implement in Android, compatible from 4.0 and beyond, been the last version tested 5.0.

## How it works
EMODIANA App is designed to measure emotion in two ways: selecting emotion and intensity (cuantitative) and explaining why (cualitative)
To do so, the app its designed as a book with several pages or steps to be fullfiled:
* **Basic information**: for statistics purpose, some data are required: name, age and gender.
* **Emotion selection**: subjetive emotional-icons are displayed so the child selects one.
* **Intensity measure**: intensity can be selected as using a rating bar from 0 to 5 (where 0 means a low intense emotion and 5 means an intense emotion)
* **Explaning why**: using a text box, the child can write how he/she feels and gives an explanation for futher analysis.

One these steps are completed, data can be saved.

## Data Storage/sending
Data is stored in internal memory of the phone so no SD-card is necessary. But that cause the data not to be accesible directly.
Data is saved in XML files, so they can be exported and imported easily.

There are two options to manipulate app's data, located in the home screen: *delete* and *send*.
The first options *delete* will erase all data from the app, and the second option *send* will compress data into a .zip and launch an email manager to send files (doing so will not delete data).
