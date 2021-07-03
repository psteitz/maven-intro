# A Gentle Introduction to Apache Maven

There are lots of great Maven books, tutorials and other online resources available.  The best, IMO, is the [Apache Maven Getting Started guide](https://maven.apache.org/guides/getting-started/).  I strongly recommend that anyone who reads this little introduction follow it with a dive into the Getting Started guide. Our goal is just to get the basic concepts across and some experience doing Java development using Maven.  

## What is Maven?
Apache Maven is an open source build system for the Java programming language. It was created initially by developers working on the [Apache Turbine](https://turbine.apache.org/) project to make it easier for new contributors to build the Turbine source code. Before Maven, most open source Java projects used [Apache Ant](https://ant.apache.org/) and / or platform-dependent shell scripts to build their code.  The scripts driving the build were often complex, including properties files that had to be configured locally.  Developers also had to download and configure locations of dependencies.  

"Dependencies" are jar files containing classes that a java project needs to compile and run its own source code. A big advantage of Maven is that it defines a structure for online repositories to host jar files published by open source projects. Maven provides developers with a standard way to specify dependencies and its runtime takes care of downloading them.

In addition to downloading dependencies, Maven also sets up the compile class path to include them and the project's source code, automatically compiles and executes unit tests and generates javadoc.  It can also generate a project website including documentation and reports.  To accomplish all of this without too much configuration, Maven relies on a standard directory layout for source files and other project resources.  As long as you set your projects up (or let Maven do it) according to the conventional directory layout, Maven takes care of everything else.

## Quick Start
### Download and Install Maven
This section assumes that you are running a unix operating system (MacOS, Linux or some other unix variant).  

Before you do anything, see if Maven has already been installed on your machine.  MacOS and some Linux distributions ship with Maven pre-installed.  Type 

    mvn --version
to see if it is installed.  If you get

    mvn: command not found 
that means Maven is not installed.  Otherwise, as long as the Maven version is at least 3, you are good to go and you can skip the following steps.

1. Download the latest Apache Maven binary distribution from the [Apache Maven Download Page](https://maven.apache.org/download.cgi). Download the binary tar.gz archive. 
2. Move the archive to the location where you want to install Maven locally.  I usually just put it in my home directory. Extract it using `tar -xzf apache-maven-3.5.4-bin.tar.gz` replacing `3.5.4` with the latest version that you downloaded.  This will create a directory named `apache-maven-3.5.4` (or newer) in your home directory that contains the maven installation.
3. Make sure that you have a working Java SDK installed on your machine. Type `java -version` at the command line. If the java version displayed is at least 1.7, you can run Maven.  If not, you need to [download and install a JDK](https://apple.stackexchange.com/questions/334384/how-can-i-install-java-openjdk-8-on-high-sierra).
4. Add the Maven bin directory to your system path. If you ran the comand in 2. from your home directory, what you need to add to the system path is `$HOME/apache-maven-3.5.4/bin` (again replacing `3.5.4` with the number of the possibly newer version that you downloaded). If you can't remember or don't know how to add things to your system path, you can follow [these directions](https://coolestguidesontheplanet.com/add-shell-path-osx/) on MacOS.
5. Verify that you have a working Maven installation by typing `mvn --version` at the command line.  The output should include the Maven version, the JDK version and other system information.

### Create a Project

Maven expects projects to conform to a standard directory structure.  It expects to find a file named `pom.xml` file in the top-leval directory.  This file, called the "pom file," holds data describing the project and how it is to be build. In addition to the pom file, Maven expects the top-level project directory to include a `src` directory including source code split into two subdirectories, one called `main` and the other called `test,` each of which contain a `java` source tree rooted at a directory named `java.`  You can use Maven itself to create this structure for a new project by typing something like:

    mvn -B archetype:generate \
        -DarchetypeGroupId=org.apache.maven.archetypes \
        -DgroupId=com.steitz.samples \
        -DartifactId=mvn-intro
        
The ``groupId`` and ``artifactId`` are the *maven coordinates* of the artifacts that the project produces.  We will talk more about these later.  Running the above command creates a directory named `mvn-intro` below the directory where you run the command and creates a `pom.xml` file in that directory.  The `mvn-intro` directory has a standard maven project layout.  The ``java`` directories under ``main`` and ``test`` have subdirectories ``java/com/steitz/samples`` where you can place code with the package name `com.steitz.samples.`  The sample code in this project uses that package name.  You can replace `com.steitz.samples` with your own package specification.  See the [package naming tutorial](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html) for guidelines on choosing package names for your projects.  

Depending on the version of maven that you used to run the command, the generated pom file should look something like this:

    <project xmlns="http://maven.apache.org/POM/4.0.0"  
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://  
      maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.steitz.samples</groupId>
      <artifactId>mvn-intro</artifactId>
      <packaging>jar</packaging>
      <version>1.0-SNAPSHOT</version>
      <name>mvn-intro</name>
      <url>http://maven.apache.org</url>
      <dependencies>
        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>3.8.1</version>
        <scope>test</scope>
       </dependency>
      </dependencies>
    </project>

You can see one dependency specified - ``junit.``  But the version specified is very old.  Use your favorite text editor to edit the file and change the `version` tag under ``junit`` to ``4.12.``  So the ``junit`` dependency section looks like

       <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
        <scope>test</scope>
       </dependency>

The archetype plugin actually also creates some source files, which you can build and test by typing

    mvn clean test
    
from the top-level project directory (``mvn-intro``).

That command downloads the ``junit`` dependency, compiles the single main source file in ``src/sc/main/java/com/steitz/samples/App.java`` and the ``junit`` unit test class in ``src/test/java/com/steitz/samples/AppTest.java`` and runs the unit test, providing the jvm with both the main sources and the ``junit`` jar in the classpath.

You will notice that it creates a ``target`` directory with a ``classes`` directory that holds ``target/classes/com/steitz/samples/App.class,`` the one project main source file and a ``test-classes`` directory containing ``target/test-classes/com/steitz/samples/AppTest.class.``  The ``target`` directory is a working directory used by the build and the place where build artifacts are stored.  The ``clean`` command recursively deletes the ``target`` directory.  If you run ``mvn clean`` now, you will se that ``target`` has vanished.

In professional programming environments, maven archetypes are usually centrally maintained, so that projects created using the maven archetype command start with a prescribed structure.  You can specify a custom archetype see [Introduction to Archetypes (https://maven.apache.org/guides/introduction/introduction-to-archetypes.html/) in the Apache Maven online docs for more information on project archetypes.

## A Simple Project
Here is a simple project. What we are building is an application that reads a csv file and performs a statistical analysis using the data in the file.  We use ``commons-csv`` to parse the csv file and ``hipparchus-stat`` to perform the statistical analysis.  We start by adding these as dependencies to our pom file.  Edit the generated pom file in `mvn-intro` to add two new dependencies so the ``dependencies`` section looks like this:

	  <dependencies>
	    <dependency>
	      <groupId>junit</groupId>
	      <artifactId>junit</artifactId>
	      <version>4.12</version>
	      <scope>test</scope>
	    </dependency>
	    <dependency>
	      <groupId>org.hipparchus</groupId>
	      <artifactId>hipparchus-stat</artifactId>
	      <version>1.3</version>
	    </dependency>
	    <dependency>
	      <groupId>org.apache.commons</groupId>
	      <artifactId>commons-csv</artifactId>
	      <version>1.6</version>
	    </dependency>
	  </dependencies>

With these dependencies added, we can now import classes from both of these open source libraries in our application code.  Maven will download the specified versions of the libraries and add them to the compile classpath as well as the to the classpath of the of the test runner when unit tests are executed.

### Setting up dependencies

We'll use Eclipse to write code, but any IDE or code editor will do.  There are more sophisticated ways to set up Eclipse with Maven. I am going to keep things simple here and use this as an opportunity to explain how maven repositorites work. If you don't use Eclipse, or have already set up the integration, you should read this anyway because the repository concepts are important.

To get Eclipse to be able to find the dependencies downloaded by Maven, run ``mvn eclipse:eclipse`` from the top level project directory.  That will download the dependencies to the local maven repo (as any other ``mvn`` command would do at this point).  It also generates a ``.classpath`` hidden file that contains lines like this:

    <classpathentry kind="var" path="M2_REPO/org/hipparchus/hipparchus-stat/1.3/hipparchus-stat-1.3.jar"/>
    <classpathentry kind="var" path="M2_REPO/org/hipparchus/hipparchus-core/1.3/hipparchus-core-1.3.jar"/>

The ``M2_REPO`` reference is an Eclipse classpath variable.  In order for Eclipse to find the dependencies so that it can understand code you write that imports classes from them, you need to set this reference to the location of the local maven repository that Maven has set up on your machine.  The default location for this is ``$HOME/.m2/repository/``.  You can get the full path displayed by typing 

    mvn help:evaluate -Dexpression=settings.localRepository 

Go to your home directory and type 

    ls .m2/repository
    
You will see a lot of directories there, including all kinds of things that you did not ask Maven to download, some resembling package names that you have seen fly by on the screen as you performed the steps above.  The structure of the repository itself resembles a java source tree.  Type

    ls  ~/.m2/repository/org/hipparchus/hipparchus-stat/
    
 There should be a subdirectory named ``1.3`` containing a jar file named ``hipparchus-stat-1.3.jar``
 
 So the full path to the locally cached copy of the dependent jar is 
 
 ~/.m2/repository/org/hipparchus/hipparchus-stat/1.3/hipparchus-stat-1.3.jar
 
The first part of this path, `` ~/.m2/repository/`` locates the repository itself.  The ``org/hipparchus/`` part corresponds to the ``groupId`` in the dependency specification above.  This is followed by the ``artifactId``, ``hipparchus-stat`` and then the ``version``, ``1.3``.  You don't need to know or use these paths yourself, as Maven handles everything for you, but it is good to understand that the triple ``<groupId,artifactId,version>`` uniquely identifies a dependent jar.  Generally groupIds look like domain and / or package names, but this is not universally true.  Using groupIds based on domain names that you do not own is a bad idea for the same reason that it's a bad idea to do that for Java package names - you could end up clashing with an official released version of something.  To make things simple and easy to follow, the examples here use ``com.steitz`` which is a domain that I have registered.  In your own code, you should use a groupId specific to yourself or your organization (like java package name choice).

To make sure that the Eclipse ``M2_REPO`` variable points to your local maven repo, in Eclipse, use the Preferences menu and then navigate to Java, Build Path, Classpath Variables and if there is no entry for ``M2_REPO`` add one and specify the value to be the full path to your local maven repo (can't use ``~`` in the path).

### Some Observations

Maven's standardization of "coordinates" for versioned, reusable libraries and declarative specification of dependencies makes it a really powerful build system.  Each component has its own pom file that declares its dependencies.This allows Maven to recursively search for all depenencies needed for a build.  The extra things mentioned above are the result of Maven's doing this. You can see a very simple example of this in the ``~/.m2/repository/org/hipparchus/hipparchus-stat/1.3`` directory. Our pom file specifies hipparchus-stat version 1.3 as a dependency.  But if you look at its pom, `` ~/.m2/repository/org/hipparchus/hipparchus-stat/1.3/hipparchus-stat-1.3.pom`` you see the dependency on ``hipparchus-core``

    <dependency>
      <groupId>org.hipparchus</groupId>
      <artifactId>hipparchus-core</artifactId>
    </dependency>
    
Maven picks up this dependency and then looks at what dependencies hipparchus-core has, and so on until it has downloaded and added all transitive dependencies to the build classpath. 

Another thing to notice at this point is that that there is no "build script" per se.  The Maven pom file is a *declarative build specification*.  It specifies things that are required for the build or that you want to be true about the build and the Maven runtime figures out what sequence of steps, temp directories, etc. are needed to execute it.

You tell Maven to do things by giving it *goals* on the command line.  Probably the simplest is ``mvn clean`` that just wipes out the ``/target`` directory that may have been created by a previous build.  That goal also downloads dependencies to prepare for the next build.  Some goals require other goals as prerequisites and Maven automatically executes the prerequisites in a standard order.  For example, ``mvn test`` will execute the ``compile`` goal for both the main and test sources if these have changed since the last time ``clean`` was executed.  You can also give Maven multiple goals in a single command.  In that case, they are executed in the order that you give them.  We saw an example of that above in ``mvn clean test.``  You could also do ``mvn test clean`` but that would not force a fresh build for the test and would end by deleting the ``/target`` directory. Technically, what I am calling "goals" here are the default goals of maven plugins. The key thing to understand is that just as the pom file declaratively specifies what you want to be true about builds of the project, so command line goals just tell Maven what outcomes you want and Maven figures out what it needs to do to achieve the goals that you provide in the sequence that you have provided them.   


### Setting properties

Project-level properties can be set using ``properties`` elements in the pom file.  One basic property that you always want to set is the jdk version used by the java compiler. If you don't spedify this property, it defaults to an ancient (pre-java 8) version that will cause compilation problems if you use any modern Java features in your code.  To set the jdk level to Java 8, add these lines right after the url element, just before the dependencies section:

    <properties>
      <maven.compiler.target>1.8</maven.compiler.target>
      <maven.compiler.source>1.8</maven.compiler.source>
    </properties>

You can also set dependency versions and define other project-level variables in this way.

### Now, Write Some Code

Either delete and replace or rename the ``App.java`` file in ``/mvn-intro/src/main/java/com/steitz/samples`` with one named ``LinearFit.java`` with the following contents:

    package com.steitz.samples;

    import java.io.File;

    public class LinearFit {

	    public static void main(String[] args) {
	        if (args.length > 0) {
	            // first command line argument is full path to the input file
	            SimpleRegression model = null;
	            final String filename = args[0];
	            final File file = new File(filename);
	            try {
	                model = new LinearFit().fitModel(file);
	            } catch (IOException ex) {
	                System.err
	                    .println("Error opening or parsing data from " + filename);
	            }
	            if (model == null || Double.isNaN(model.getSlope())) {
	                System.err
	                    .println("Error estimating the model from " + filename);
	            } else {
	                showResults(model);
	            }
	        } else {
	            System.out.println("Please provide the input file name");
	        }
	    } 
	    
	   public SimpleRegression fitModel(File dataFile) {
	        return null;
	   }
	
	    private static void showResults(SimpleRegression model) {
	        return;
	    }
    }
    
This code doesn't do anything.  If you run it, it will just display an error message.  Let's go test first and first develop a test case that will fail for now.  Either rename or replace the ``AppTest.java`` file in ``/mvn-intro/src/test/java/com/steitz/samples`` with one named ``LinearFitTest.java`` with the following contents:

    package com.steitz.samples;

	import java.io.File;
	import java.net.URL;
	
	import org.hipparchus.stat.regression.SimpleRegression;
	import org.junit.Test;
	import static org.junit.Assert.assertEquals;
	
	/**
	 * Test cases for LinearFit class.
	 */
	public class LinearFitTest {
	    // Test data and expected results are from 
	    // https://www.itl.nist.gov/div898/strd/lls/data/LINKS/DATA/Norris.dat
	    private static final String TEST_FILE = "norris.csv";
	    private static final double TOL = 1e-12;
	    
	    @Test
	    public void testParameterEstimates() throws Exception {
	        final URL url = this.getClass().getResource(TEST_FILE);
	        final File file = new File(url.getFile());
	        final LinearFit fitter = new LinearFit();
	        final SimpleRegression model = fitter.fitModel(file);
	        assertEquals(1.00211681802045, model.getSlope(), TOL);
	        assertEquals(-0.262323073774029, model.getIntercept(), TOL);
	    }
    }

The @Test annotation tells the test runner to run ``testParameterEstimates.`` 
The first thing that this test case needs to do is to provide a path to a test file that the test can load data from. The easiest way to set this up it to put the test file in the system classpath. Maven does this for you if you put the file in ``src/test/resources``.  More precisely, Maven first compiles the project source code (from ``src/main/java``) and writes the class files to ``/target/classes``.  Then it compiles the test code with these classes on the compile classpath and puts the compiled test classes into ``/target/test-classes``.  

### Packaging and distribution

The pom file in our simple application includes the lines

    <artifactId>mvn-intro</artifactId>
    <packaging>jar</packaging>
    <version>1.0-SNAPSHOT</version>
    
These lines tell Maven to produce a jar file named ``mvn-intro-1.0-SNAPSHOT.jar`` containing all compiled classes from ``/src/main/java.``  Note the form of the file name: 

    artifactId-version.jar.   

A default manifest is generated and included in the jar file.  Manifests can be customized, resources can be added and classes can be filtered by adding various configuration elements to the pom.

### Making the jar executable

The jar created above is not executable and it does not include the dependencies needed for it to run.  To create an "all-in-one" jar, we need to add a few more lines to the pom.

Immediately after the ``dependencies`` section of the pom, we need to add a ``build`` section with the following lines

    <build>
	    <plugins>
	      <plugin>
	        <artifactId>maven-assembly-plugin</artifactId>
	        <configuration>
	          <archive>
	            <manifest>
	              <mainClass>com.steitz.samples.LinearFit</mainClass>
	            </manifest>
	          </archive>
	          <descriptorRefs>
	            <descriptorRef>jar-with-dependencies</descriptorRef>
	          </descriptorRefs>
	        </configuration>
	        <executions>
	          <execution>
	            <id>make-assembly</id> 
	            <phase>package</phase> 
	            <goals>
	              <goal>single</goal>
	            </goals>
	          </execution>
	        </executions>
	      </plugin>
	    </plugins>
    </build>

These lines bind the ``assembly`` plugin to the ``package`` phase of the build (i.e. they make ``mvn package`` invoke the ``assembly`` plugin).  The ``archive/manifest/mainClass`` element specifies the ``LinearFit`` class from our project as the main class (the one whose ``main`` method will be executed when we type ``java -jar mvn-intro-1.0-SNAPSHOT-jar-with-dependencies.jar <path to data file>`` at the command line.  The ``jar-with-dependencies`` descriptor reference refers to a prebuilt assembly descriptor that extracts all classes from all dependent jars and bundles them into the output jar.  

Add the ``build`` section above at the same level after the ``dependencies`` section and then do ``mvn package`` again from the top-level project directory.  That should create ``intro-1.0-SNAPSHOT-jar-with-dependencies.jar`` in ``/target.`` If you look inside that jar, you will see dependent classes from ``org.hipparchus`` and ``org.apache.``  

From ``/target`` 

    java -jar mvn-intro-1.0-SNAPSHOT-jar-with-dependencies.jar ../src/test/resources/com/steitz/samples/norris.csv 
    
should execute the current code with the test file as input, producing this output:

    Regression Results 
    Number of observations: 36
    Estimated model y = b_0 + b_1xb0 = -0.26232307377414243
    b1: 1.0021168180204547
    b_0 standard error: 0.2328182342925303
    b_1 standard error: 4.297968481840198E-4RSquare: 0.9999937458837121
   
## Reports and project websites
### Plugins and their configuration
Maven builds use plugins to perform build tasks.  You can change the behavior of a build by adding or removing plugins or by changing plugin configurations.  The changes made above to make the jar produced by the ``mvn package`` executable are an example of this.  In that example, the added configuration elements that tell the build to create a single jar (including dependencies). By default, the created jar is executable, directing to the main class specified in the ``mainClass``configuration element.
### Reporting plugins
Reports are configured in the (top-level) ``reporting`` element. For example, to get a javadoc report generated, add this after the ``build`` element

    <reporting>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-javadoc-plugin</artifactId>
          <version>3.3.0</version>
         </plugin>
      </plugins>
    </reporting>

After adding the reporting section with the javadoc plugin specified, if you execute ``mvn site`` a project web site will be generated in the ``target/site`` directory.  You can find the javadocs there.

You can generate diagnostic reports checking for common coding errors, test coverage and many other things by adding more reporting plugins.  A commonly used plugin is ``checkstyle``.  To bring this in with default configuration, add another plugin to the reporting plugin list:

    <reporting>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-javadoc-plugin</artifactId>
          <version>3.3.0</version>
         </plugin>
      </plugins>
    </reporting>

After adding this, ``mvn site`` will generate a Checkstyle report where you will find various complaints about coding style.  What kinds of checks Checkstyle performs is configurable in different ways.  See the [maven-checkstyle-plugin docs](https://maven.apache.org/plugins/maven-checkstyle-plugin/) for instructions.

## Learning more
There is a lot more to learn about maven, but fortunately [Apache Maven Website](https://maven.apache.org) has great documentation and like all things open source, you can learn a lot just by looking at pom files from open source java projects. The [Apache Maven User List](https://maven.apache.org/mailing-lists.html) is also a generally friendly place where you can get answers to questions.  Just do a little googling and search the archives first to see if your question has already been answered. 


 
 



      
   

