from bs4 import BeautifulSoup

file = "../../xml_files_windows/standard.xml"

soup = BeautifulSoup(open(file), "xml")

print soup.data.name
print soup.data.string