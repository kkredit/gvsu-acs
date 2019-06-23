
# Hands on with HTTP
Original
[link](https://cis.gvsu.edu/~kurmasz/Teaching/Courses/W19/CIS658/Homework/explore_http.html).

## Purpose
We're going to explore the HTTP protocol first-hand by examining its behavior when we interact with
the various websites. We will use Chrome's Developer Tools to examine the underlying HTTP behavior.
(Other web browsers have tools similar to Chrome's Developer Tools.)

## Steps

- Familiarize yourself with the built-in developer features in Chrome, or equivalent features in
other browsers. (From the Chrome menu, go to More Tools -> Developer Tools. If you have trouble see
[this
diagram](https://cis.gvsu.edu/~kurmasz/Teaching/Courses/W19/CIS658/Homework/openChromeDevTools.png)
.)

- Open a private browsing window. (This step is important.)

- Open the Developer Tools, click on the "Network" tab, and check the "Disable cache" box

- With Developer Tools open, click on the tab labeled "Network", then load the page
https://cis.gvsu.edu/~kurmasz/Humor/engin.html
*Important: Make sure you areusing https not http*

- Select and expand the HTTP message labeled "engin.html". Make sure the Headerstab is selected,
then look through the General section for the Status Code.

#### 1. What is the Status Code; and what does it signify?
200 -- OK. This means no error.

#### 2. What would the status code be if you mis-typed "engin"?
404 -- Not Found.

#### 3. Test your answer to the previous question by trying to fetch https://cis.gvsu.edu/~kurmasz/Humor/engen.html
Confirmed.

- Examine the request headers for your call to https://cis.gvsu.edu/~kurmasz/Humor/engin.html.

#### 4. Are request headers sent by the browser or the web server?
Browser.

#### 5. What is the value of the User-Agent header?
Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
Chrome/71.0.3578.98 Safari/537.36

#### 6. What is the purpose of the User-Agent header?
To tell the server what environment and browser I am running.

#### 7. What is the value of the Accept header?
text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8

#### 8. What is the purpose of the Accept header? (Hint: Look [here](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html).)
To tell the server what kind of media I can handle.

#### 9. What is the value of the Cache-Control header?
no-cache

#### 10. Why is Cache-Control set that way?
Because we disabled caching in a previous step.

- Now, examine the response headers for your call to https://cis.gvsu.edu/~kurmasz/Humor/engin.html.

#### 11. What is the value of the Content-Type header?
text/html

#### 12. What is the purpose of the Content-Type header?
To tell the client what type of media it is receiving.

#### 13. List the other response headers and briefly describe their purpose.
Accept-Ranges: tell the client what types of ranges the server accepts
Connection: tell the client how the server is handling the connection (closed vs persistent, etc)
Content-Length: how big the returned data is
Date: timestamp
ETag: entity tag for the requested variant
Last-Modified: date the data was last modified
Server: information about the source server type

Source: the website linked above under "Look here".

- View the actual payload of the HTTP response by clicking on the "Response" tab.

#### 14. What kind of data was received? Does it match the Content-Type header?
It is HTML, though it doesn't have <html> anywhere in the source. It does match the content-type
value of text/html.

- Uncheck the "Disable Cache" box and reload the page.

#### 15. What is the status code? (If the Status Code is still 200, try refreshing again.)
304 -- Not Modified.

#### 16. What is the purpose of this status code? What is the benefit of returning this code instead of 200?
It indicates that the cached page was used instead of sending a new request. This provides more
information to the client about what is actually going on.

- Load the page http://cis.gvsu.edu/~kurmasz/Humor/engin.html (notice http instead of https)

#### 17. What is the status code? What does this status code indicate?
302 -- Found. It means the resource exists.

#### 18. What previously unseen response header did the server return when you requested the "http" page?
Location, containing the "https" URL.

#### 19. How did your browser respond to this code?
It subsequently sent a GET request for the provided "https" URL.
