#!/usr/bin/env python
# coding: utf-8

# # Extract name, latitude, longitude and population

# In[1]:


# Import the necessary package

import requests
import json
import pycountry
import os


# In[2]:


# Functions for checking if coordinates are in correct ranges and if country code is ISO3166 alpha-2 code 

def checkLat(Coord):
    try:
        if (float(Coord) >= -90) & (float(Coord) <= 90):
            return True
        else: 
            print('The latitudes must be in the range of -90 to 90:')
    except ValueError:
        print('Please state a number in the range of -90 to 90:')
    
    
def checkLong(Coord):
    try:
        if (float(Coord) >= -180) & (float(Coord) <= 180):
            return True    
        else: 
            print('The longitudes must be in the range of -180 to 180:')
    except ValueError:
        print('Please state a number in the range of -180 to 180:')
        
def checkIso(Country):
    iso_codes = []
    for cc in pycountry.countries:
        iso_codes.append(cc.alpha_2)
    if Country in iso_codes:
        return True
    else:
        print(Country + ' is not a valid ISO3166 alpha-2 code. See https://www.nationsonline.org/oneworld/country_code_list.htm or listofcountrycodes.csv')


# In[3]:


# Define the saving path

save_path = os.getcwd()
print(save_path)
#save_path = 'C:/Users/Qusai/IdeaProjects/citygraph'
#save_path = 'C:/Users/eschlager/Documents/Hidalgo/LocationsGraphExtraction/Data/'


# ## USER INPUT

# In[4]:


# Specify the type; city/town/village

for i in range(0,1000):
    PLACE = input("Specify the type of place (city or town or village): ")
    if PLACE == 'city':
        break
    if PLACE == 'town':
        break
    if PLACE == 'village': 
        break
    else:
        print("Choose between city or town or village:")


# In[5]:


# Specify if region per country of coordinates

for i in range(0,1000):
    TYPE = input("Do you want to define the region by country code (1) or by the coordinates (2): ")
    if TYPE == '1' or TYPE == '2': 
        break
    else:
        print("Choose 1 for country code or 2 for coordinates:")


# In[6]:


# Give the specific country or the coordinates

if TYPE == '1':
    for i in range(0,1000):
        COUNTRY = input("State the ISO3166 alpha-2 code of the country: ")
        if checkIso(COUNTRY):
            break

elif TYPE == '2':
    ''' e.g.:
    South coordinate: 20
    West coordinate: 68
    North coordinate: 25
    East coordinate: 74
    '''
    
    for i in range(0,1000):
        SOUTH = input("South coordinate: ")
        if checkLat(SOUTH): 
            break
    
    for i in range(0,1000):
        WEST = input("West coordinate: ")
        if checkLong(WEST): 
            break
        
    for i in range(0,1000):
        NORTH = input("North coordinate: ")
        if checkLat(NORTH): 
            break
            
    for i in range(0,1000):
        EAST = input("East coordinate: ")
        if checkLong(EAST): 
            break      


# ## QUERY

# In[7]:


# Create the queries:

if TYPE == '1':
    overpass_query = """
    [out:json];
    area["ISO3166-1"= %s ][admin_level=2];
    (node[place= %s ](area);
    );
    out center;
    """ % (COUNTRY, PLACE)
elif TYPE == '2':
    overpass_query = """
    [out:json];
    (node[place=%s](%s,%s,%s,%s);
    );
    out center;
    """ %(PLACE, SOUTH, WEST, NORTH, EAST)


# In[8]:


# Pass the request

overpass_url = "http://overpass-api.de/api/interpreter"

response = requests.get(overpass_url, 
                        params={'data': overpass_query})
data = response.json()


# In[9]:


#input("Give the download a few seconds: ")


# In[10]:


# Save as Json file

with open(save_path + '/personal.json', 'w') as json_file:
    json.dump(data, json_file)


# ## DATA EXTRACTION

# In[11]:


# Load the json file

with open(save_path + '/personal.json') as json_file:
    data = json.load(json_file)


# In[12]:


# For a single part

# print(data['elements'][0]['lat'])
# print(data['elements'][0]['lon'])
# print(data['elements'][0]['tags']['name'])
# print(data['elements'][0]['tags']['population'])


# In[13]:


# Loop

#Create a data frame with the columns "name", "lat", "lon" and "population"
import pandas as pd
locations = pd.DataFrame(columns=['name', 'lat', 'lon', 'population'])
#locations

#Create empty lists of the variables
LAT = []
LON = []
NAME = []
POP = []


# In[14]:


n = len(data['elements'])


# In[15]:


# Read Latitude, longitude, name and population of city/town/villages

for i in range(0,n):
    try:
        LAT.append(data['elements'][i]['lat'])
    except KeyError:
        # if latitude not given, it's set to 1000
        LAT.append(1000)
    
for i in range(0,n):
    try:
        LON.append(data['elements'][i]['lon'])   
    except KeyError:
        # if longitude not given, it's set to 1000
        LON.append(1000)
    
for i in range(0,n):
    try:
        NAME.append(data['elements'][i]['tags']['name'])
    except KeyError:
        # if name not given, the name is set to an empty string
        NAME.append('')
    
for i in range(0,n):
    try:
        POP.append(data['elements'][i]['tags']['population'])
    except KeyError:
        # if population not given, the population is set to 0
        POP.append(0)


# In[16]:


datalist = {'name': NAME,
            'lat': LAT, 
            'lon': LON, 
            'population': POP
           } 


# In[17]:


locations = pd.DataFrame(datalist) 


# In[18]:


# locations[locations.name == '']
locations.head()


# In[19]:


# Save csv

locations.to_csv(save_path + '/cities.csv')


# In[20]:


# Delete json file to avoid future conflicts

os.remove(save_path + '/personal.json')


# In[ ]:




