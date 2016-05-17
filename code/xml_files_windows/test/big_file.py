my_file = open("output.xml", "r+")

my_file.write("<data>" + "\n")

for i in range (1,5000000):
    my_file.write("<country>" + str(i) + "</country>" + "\n")

	
my_file.write("</data>" + "\n")
my_file.close()
