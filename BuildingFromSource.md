# Introduction #

The following steps will help you build FBPwn from source using [Netbeans](http://Netbeans.org) IDE.


# Details #

## Getting the code ##

You need to checkout the code, There are 2 ways mentioned in this wiki to do this.

1. Using command-line subversion client
> If you have subversion command-line client installed, you can run the following command to get the source code:

> `svn checkout http://fbpwn.googlecode.com/svn/ fbpwn-read-only`

2. Using Netbeans
> Simply, from the menu bar, click on: `"Team" --> "Subversion" --> "Checkout"`. Enter the following URL: `"http://fbpwn.googlecode.com/svn/"` and follow the instructions mentioned in the wizard.

## Creating the project ##

Now you need to create a new Java project in your Netbeans. From the menu bar click ` "File --> New project --> Java --> Java Application"`. Name it "FBPwn", and un-check "Create Main Class". Click finish.

## Configuring the project ##

Now we need to add the source files and the libraries into our project. Navigate to the folder where you checked out the source code, and copy both "src" and "lib" folders inside your project's folder such that you overwrite the "src" folder that already exists.

Next, from inside Netbeans, right click on your project and click "Properties", go to the "Libraries" tab and click "Add Jar/Folder". Add all the .jar files inside "lib/htmlunit-2.8/lib/" folder and also those inside "lib/substance/" folder.


You should now be ready to build and run the project.