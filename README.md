# This is an app that calls REST services from https://jsonplaceholder.typicode.com
# Technologies Used: Android threading and network calls such as ExecutorService, Callable, and Future

# Functionalities:

1. When the app loads, it performs a Get Request to 
   https://jsonplaceholder.typicode.com/users. It loads information about 10 users

2. The RecyclerView displaying the list of users is interactive; upon clicking a user, the application navigates to a detailed view showing
   comprehensive information about the selected user. This information includes the user's phone number, username, address, email, website, and company details.
