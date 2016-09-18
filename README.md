# PDF2HTMLWrap

##Requirements
You will require the following conditions to run the program:
- JVM installed on the machine
- CLI tool to execute the jar

##For End Users
The built standalone jar file is able to extract bibliography items from a specified PDF document or a specified list. You can perform this task by typing the following command into a CLI program:

    java -jar [name].jar [input files] [output folder]

where you replace
- name with the name of the standalone jar file
- input files with the paths to the list of files you wish to extract the bibliography from, each separated with a space. Such as "C:/users/file1.pdf C:/users/file2.pdf"
- output folder with the path to the directory you wish to store the output

It will store the output HTML file with the name of the corresponding pdf.

##For Developers
This code functions by using a modification of the Apache PDFBox library. The modification was created by CSSBox, known as PDFtoHTML.

PDFtoHTML is a standalone tool, this code wraps it to allow multiple input capabilities, with each input run on a separate thread for multi-tasking.

The source code is explained below.

###Package - main
Responsible for starting and running extractor classes.
