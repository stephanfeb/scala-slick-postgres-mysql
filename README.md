
## Getting Started: Scala SQL DB access using Slick + PostgreSQL or MySQL

This repository contains demo code for connecting to a SQL database using
the [Slick FRM](http://slick.typesafe.com/doc/3.1.0/)

This is a purposely minimalist take on DB access to demonstrate the
bare essentials to get started with setting up your code base.  

You can read a little more about the code in my blog post : [BLOG POST LINK]()

### HOWTO

#### Checkout the code
```shellSession
Stephans-MacBook-Air: stephan$ git clone https://github.com/stephanfeb/scala-slick-postgres-mysql.git
Cloning into 'scala-slick-postgres-mysql'...
remote: Counting objects: 12, done.
remote: Compressing objects: 100% (6/6), done.
remote: Total 12 (delta 0), reused 12 (delta 0), pack-reused 0
Unpacking objects: 100% (12/12), done.
Checking connectivity... done.
```

#### Run SBT in the top-level directory
```shellSession
Stephans-MacBook-Air: stephan$ cd scala-slick-postgres-mysql/
Stephans-MacBook-Air:scala-slick-postgres-mysql stephan$ sbt
[info] Set current project to slickStart (in build file:/Users/stephan/IdeaProjects/scala-slick-postgres-mysql/)
```

#### Run using the ```run``` command
```shellSession
> run
[info] Updating {file:/Users/stephan/IdeaProjects/scala-slick-postgres-mysql/}scala-slick-postgres-mysql...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[warn] Scala version was updated by one of library dependencies:
[warn]  * org.scala-lang:scala-library:2.10.4 -> 2.10.5
[warn] To force scalaVersion, add the following:
[warn]  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
[warn] Run 'evicted' to see detailed eviction warnings
[info] Compiling 1 Scala source to /Users/stephan/IdeaProjects/scala-slick-postgres-mysql/target/scala-2.10/classes...
[info] Running Application
[success] Total time: 5 s, completed Oct 14, 2015 10:24:47 PM
> Seeya!
Superior Coffee: 1 Party Place : Mendocino
Acme, Inc.: 99 Market Street : Groundsville

>
```

### NOTE
At the time of writing, this code uses Slick version 3.1.0  
Slick is a FRM (functional relational mapper), as opposed to an ORM (e.g. Hibernate).  
Slick does not currently have support for NoSQL databases like MongoDB or Cassandra  

