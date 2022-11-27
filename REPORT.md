# IDATA2304 Temperature Logger

This is a school project in the course IDATA2304 Computer Networks for the students within the computer engineering course 2022 on NTNU.
The project is part of our evaluation within the course with a 40% weight on out grades.  

## Abstract

Temperature is something that effects us everyday.

This is the shortest version of your project's description. Think of a busy
person who has 1 minute to get familiar with what this is about. The abstract
should be short but descriptive.
Suggested content in the abstract:

* Describe the background and importance of the situation, 1-2 sentences
* Describe a problem, 1-2 sentences
* Describe your proposed solution, 1-3 sentences
* Describe the results you have achieved, 1-3 sentences
* Describe the experiments or evaluation you have done
* Conclusions and possible future work



Here is an example abstract of an imaginary project:
Modern urban lifestyle has high demands on the individuals. We have busy
schedules and need to remember many things. One of the most irritating things in
a hectic morning is the inability to find your keys or wallet. In this
project we propose a novel solution for tracking of daily-life objects, such as
your wallet or keychain. Users attach smart chips with Bluetooth communication
to each important asset they want to track. This gives the ability to use a
smartphone to locate the missing item. We have created a prototype system,
described in this report. We have performed a user study with 20 participants
who were given the task to locate a wallet inside a living room.
The results show that our solution helps the users to locate their items within
2 minutes the first time, and within 45 seconds when they are using the app
repeatedly. Possible future research directions include design of a robust and
lightweight RFID tag, and possibilities to locate ones items using a mobile
phone of a family member.

Note: in your project you may not have user tests. Describe the analysis and
evaluation you have had.

## Introduction

Here you introduce your project in more detail. Include the following:

* Introduction of the context, the domain. Where will your solution work? Is
  this the maritime domain, finance, private homes?
* Introduction of the problem. What is problematic in this environment? What
  will you solve? Why is this relevant?
* Short introduction in the rest of the report, preferably with links to the
  other chapters. For example, "We propose an Internet-of-Things system using
  temperature and humidity sensors. First we describe the used
  protocols, ["theory and technology"]. Then we describe our work process
  in ["Methodology"]. Then the obtained [results] are presented, followed by
  [reflection and discussion of possible improvements]." Note: don't copy this
  text, write your own!

We have made an application that measures and records the temperature and humidity 
in a room throughout the day via a temperature- and humidity-sensor.
There are many benefits to monitoring your indoor environmental conditions with an application such as this. 
One of the most important reasons to use a sensor like this, 
is to make sure that you are maintaining a healthy indoor air quality in the various rooms in your house, 
such as your living room, bedroom, and bathroom. 
Because if you don’t, you may risk affecting your, and others’, health.

As previously stated, poor indoor air quality can bring some unfortunate consequences.
Mould in the house can cause sickness like a stuffy nose, sore throat,
coughing, burning eyes and rashes on your skin.
If you are allergic to mould, depending on the severity of the allergy,
can lead to very serious reactions, and hospital visits.
It has also been documented that the proper temperature and humidity has direct impact on the quality of your sleep.

If you have been having trouble getting a good night’s sleep, 
it might be worth taking into consideration the temperature and humidity in your bedroom during the night. 
Depending on where your bedroom windows are located, and the climate your country experiences, 
you might be having some humidity and temperature swings during the night that can negatively influence your sleep. 
Research shows that the ideal temperature for a bedroom is around 14-18 degrees Celsius. 
The proper humidity in a bedroom fluctuates depending on the season, 
but should be around 50% in the winter and a little higher in the summer, but not above 60%.
Using our temperature and humidity sensor could assist greatly in making sure your sleep conditions are optimal, 
so that you wake up rested and can live a healthy and happy life.

These are just a few examples of the importance of our project 
and what problems in our every-day lives it can help to solve.

## Theory and technology

Since we build our own server and the client were also written in java we decided to use the ObjectInput and ObjectOutput streams so we could send objects back and forth. This was so the implementation of both sides could be easier done, and 

These were highly inspired by the HTTP standard, both to allow for a good and fast implementation of allowing the HTTP protocols, and because the standard is one of the most recognizable.

The project is highly reliant of the TCP protocol because we needed the reliability for the packages not becoming lost or corrupted. UDP was not a alternative since we were not "sending enough packages often enough". The only case within the project were that would be reliable enough was if you had enough sensors in a room to where inaccurate data would not be a problem anymore.



Here you write about the "things" you have used in your project. At the same
time these are things that another person must know about to be able to
understand your project.
Some principles to follow:

* Write about all the relevant theory, technologies and protocols that your
  project builds upon. For example, if you transfer data in JSON format using
  the HTTP protocol, you should mention this and other protocols that it depends
  on:
    * HTTP
    * JSON
    * TCP
    * IP
    * Ethernet or wireless protocols you have used (is it 802.11x or something
      else?)
* Remember to mention the "why" - how is this "thing" you write about relevant
  to your project? What does this protocol provide for your project? For
  example, if you mention TCP - how is it important for your project? What if
  you took away TCP, what would happen? What does TCP ensure for you?
* Assume that the reader is your peer student - a computer science bachelor
  student, midway through the study. Someone taking this course next year should
  be able to read your report and understand it.
* Don't go too deep. For example, you don't need to explain a lot of detail of
  object-oriented programming. Every computer science student should know what
  it is.
* Prefer short description of many protocols instead of deep description of few
  protocols.
* Are there any specific aspects which are relevant for your project? If not,
  don't describe those. For example, students sometimes spend several pages
  describing the different methods (GET, POST, PUT) of HTTP protocol. Is that
  important for your project? Do you use all these methods? If not, don't write
  about these.
* Is there any domain-knowledge the reader should know to understand the
  project? For example, if you are monitoring temperature in a greenhouse, what
  is known about it? Is the optimal temperature +20..30C, or is it -10..0C?
* At the same time, remember that the focus of the course is computer networks
  and networking protocols. Therefore, use more time describing
  networking-related concepts.
* Describe the theory and techniques have you used for data simulation,
  processing and visualization?
* What connection to your other subjects does this project have? Does it use any
  methods learned from the IDATA2302 Algorithms and data structures? Maybe
  something from IDATA2303 Data modelling and databases? Or maybe you apply some
  statistical methods from ISTA1003 Statistics?

## Methodology

We have focused on working in groups while giving us different tasks 

Here you can write about the way you have worked. You don't need to write much
about how you organized sprints, this documentation will be handled separately.
Again - think about the next engineer-reader. What does the engineer need to
know about the way you worked? Did you do some user tests? Experiments? How did
you measure, evaluate? Any best-practices you followed? What must the reader
understand to be able to interpret the results properly?

## Results

Here you describe the results you have obtained. Some considerations:

* Have a top-down approach. Start with a short introduction, then go more into
  details. For example, a good way to start the section could be with a picture
  showing the overall architecture of the solution and a short text describing
  it. After that you can go into more details on each component of the system.
* Describe the structure you got, the general principles of how it works.
* You could also include some screenshots showing the system. How could the
  reader get an impression of the result without running the system?
* No need to include code in the report, all the code is in the repository.

## Discussion

Here you can reflect on the result. What is working well? What is not working
well and why?

## Conclusion and future work

While the project did not have all the functionality we wanted it was still a interesting and cool project to work on. We were able to create a minimal required product from scratch, and code that we could hopefully use in further projects that uses network.

While we have created the minimal required product we still feel like the project has so much more potential. For example one, we originally wanted to use HTTP to communicate with the sensors to allow small micro-controllers, like arduino that run different languages, to communicate with the server. A general list would be finishing the features on the back-end.

In general 

Here you summarize the work shortly, the status. Also, here you identify the
potential work in the future. Note: think in general - how could this work be
continued (by your group or by others)?

## References

Here you provide sources of information. In a written report you typically
include list of references in the end and have only links to those in the text,
such as [1], [2], [3]. In markdown (as this document will be) you can include
most of the links directly in the text. Here in this section you should list the
sources of information you have used - books, articles, Wikipedia articles,
other online articles. For each of them you should specify at least the title,
the author. If available: web link and year when this was published.

Note: YouTube videos are not a good source for information... Some of them are
very good, but in general YouTube is a large trash bin, where some things turn
out to be "edible".