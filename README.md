# Google-Place-Search

### User story/ User requirements:

The desktop software should integrate Google Place Search API & show results into a table.
* Scenario One: When submitting the search button there should be validation asking for a value input in the search field.

* Scenario Two: When filling in search string (ex. Apple Miami) there should be a backend search triggered to Google Search API and return in a table under the search bar a list   of all the businesses found.

* The window of the application should be resizable
### Steps to automate:

* Search on google with a keyword.
* Wait for all the search results to finish loading.
* Calculate the total number of websites to be found
* Check for the contact us page from each search result.
* Collect email address or phone number from each website
* Continue until all the website have been checked
* Check if there is any next page available or not. If not then it’s the last page
* If there is a next page available on google, go to the next page and repeat all the steps

### Final result :

[![Click to watch](doc/Screenshot_18.png)](https://www.youtube.com/watch?v=CBoL_2pau-4 "Click here to watch")
