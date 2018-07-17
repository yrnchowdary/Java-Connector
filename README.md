
# Java-Connector
Java Wrapper for Loop54 JSON V3 API

## How to install
The Loop54 Connector is available for download as a NuGet package. For installation instructions, see https://www.nuget.org/packages/Loop54.Connector/.

Requires Java 1.8 or later.

## How to use
The Loop54 Connector is easily configured if running Spring.

### Spring
Simply add the following code to the Spring configuration class marked with `@Configuration`:

    @Bean
    public ILoop54Client loop54Client() {
        Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com");
        return new Loop54Client(
            new RequestManager(settings),
            new SpringRemoteClientInfoProvider()); //Will use Spring classes to extract user data
    }

An `ILoop54Client` is then injectable by using the `@Autowire` annotation

#### Multiple instances

If you are using multiple instances of the Loop54 engine within you application you instead need to do the following:

	@Bean
    public ILoop54ClientProvider loop54ClientProvider() {
        return new Loop54ClientProvider(
                new SpringRemoteClientInfoProvider(),
                Loop54SettingsCollection.create()
                        .add("english", "https://helloworld-en.54proxy.com")
                        .add("swedish", "https://helloworld-se.54proxy.com"));
    }

And then inject the `ILoop54ClientProvider` interface into your application with the `@Autowire` annotation. And obtain 
a specific instance using the `getNamed` method with the same name as above:

	ILoop54Client client = clientProvider.getNamed("Swedish");

### Using the `ILoop54Client`
The `ILoop54Client` contains methods for making all public API calls to the Loop54 e-commerce search engine. It contains 
both synchronous and asynchronous variants of all methods.

    string searchQuery = "banana";
    SearchRequest request = new SearchRequest(searchQuery);
    SearchResponse response = loop54Client.search(request);
    
To add parameters to the `SearchRequest` you could do this:

    request.resultsOptions.skip = 0;
    request.resultsOptions.take = 20;
    request.resultsOptions.addDistinctFacet("Manufacturer");
    request.resultsOptions.addRangeFacet("Price");

All other request types also have their own types to use:

    CreateEventsRequest createEventRequest = new CreateEventsRequest(event);
    
    AutoCompleteRequest autoCompleteRequest = new AutoCompleteRequest(query);
    
    GetRelatedEntitiesRequest getRelatedEntitiesRequest 
        = new GetRelatedEntitiesRequest(entityType, entityId);
        
    GetEntitiesRequest getEntitiesRequest = new GetEntitiesRequest();
    
    GetEntitiesByAttributeRequest getEntitiesByAttributeRequest 
        = new GetEntitiesByAttributeRequest(attributeName, attributeValue);

### But wait! I'm not using Spring
There is still hope for you. If not using the above mentioned frameworks you can implement some of the functionality 
yourself and use the client just as well. You will need to implement the `IRemoteClientInfoProvider` interface and the `IRemoteClientInfo` interface and after doing that you can create a new instance of `ILoop54Client` like this:

    IRemoteClientInfoProvider myRemoteClientInfoProvider = new MySuperAwesomeCustomRemoteClientInfoProvider();
    Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com");
    ILoop54Client loop54Client = new Loop54Client(new RequestManager(settings), myRemoteClientInfoProvider);
    
And you are good to go!

## Features
- Native support for search, autoComplete, createEvent, getEntities, getEntitiesByAttribute and getRelatedEntities call. 
With intuitive APIs to call them.
- Handles user identification using random-generated cookies.
- Uses X-Forwarded-For as client IP if it's available.
- Configurable HTTP timeout.
- GZIP support.
- Relays HTTP data to engine:
    - Referer
    - UserAgent
    - Library version