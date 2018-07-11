package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;
import com.loop54.model.request.parameters.Event;

import java.util.ArrayList;
import java.util.List;

/** This class is used to configure a GetEntities request to the Loop54 e-commerce search engine. */
public class CreateEventsRequest extends Request {

    public CreateEventsRequest()
    {
    }

    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="evt">An event to add to the request. Can be of type <see cref="ClickEvent"/>, <see cref="AddToCartEvent"/>, <see cref="PurchaseEvent"/> or <see cref="CustomEvent"/>.</param>

    /**
     *
     * @param evt An event to add to the request. Can be of type {@link #ClickEvent}, {@link #AddToCartEvent}, {@link #PurchaseEvent} or {@link #CustomEvent}.
     */
    public CreateEventsRequest(Event evt)
    {
        events.add(evt);
    }

    /// <summary>
    /// The events to send in the request. Usually clicks and addtocarts are sent by themselves but multiple purchase events is usually sent as one request.
    /// </summary>
    public final List<Event> events = new ArrayList<>();
}