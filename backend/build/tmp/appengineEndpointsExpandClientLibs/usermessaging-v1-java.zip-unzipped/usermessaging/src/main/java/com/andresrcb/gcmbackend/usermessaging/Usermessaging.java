/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-05-04 15:59:39 UTC)
 * on 2016-05-14 at 14:20:59 UTC 
 * Modify at your own risk.
 */

package com.andresrcb.gcmbackend.usermessaging;

/**
 * Service definition for Usermessaging (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link UsermessagingRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Usermessaging extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.22.0 of the usermessaging library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://academic-pillar-124918.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "usermessaging/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Usermessaging(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Usermessaging(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the UserMessagingEndpoint collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Usermessaging usermessaging = new Usermessaging(...);}
   *   {@code Usermessaging.UserMessagingEndpoint.List request = usermessaging.userMessagingEndpoint().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public UserMessagingEndpoint userMessagingEndpoint() {
    return new UserMessagingEndpoint();
  }

  /**
   * The "userMessagingEndpoint" collection of methods.
   */
  public class UserMessagingEndpoint {

    /**
     * Create a request for the method "userMessagingEndpoint.messageUser".
     *
     * This request holds the parameters needed by the usermessaging server.  After setting any optional
     * parameters, call the {@link MessageUser#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.andresrcb.gcmbackend.usermessaging.model.MessageRecord}
     * @return the request
     */
    public MessageUser messageUser(com.andresrcb.gcmbackend.usermessaging.model.MessageRecord content) throws java.io.IOException {
      MessageUser result = new MessageUser(content);
      initialize(result);
      return result;
    }

    public class MessageUser extends UsermessagingRequest<com.andresrcb.gcmbackend.usermessaging.model.MessageRecord> {

      private static final String REST_PATH = "messageUser";

      /**
       * Create a request for the method "userMessagingEndpoint.messageUser".
       *
       * This request holds the parameters needed by the the usermessaging server.  After setting any
       * optional parameters, call the {@link MessageUser#execute()} method to invoke the remote
       * operation. <p> {@link
       * MessageUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.andresrcb.gcmbackend.usermessaging.model.MessageRecord}
       * @since 1.13
       */
      protected MessageUser(com.andresrcb.gcmbackend.usermessaging.model.MessageRecord content) {
        super(Usermessaging.this, "POST", REST_PATH, content, com.andresrcb.gcmbackend.usermessaging.model.MessageRecord.class);
      }

      @Override
      public MessageUser setAlt(java.lang.String alt) {
        return (MessageUser) super.setAlt(alt);
      }

      @Override
      public MessageUser setFields(java.lang.String fields) {
        return (MessageUser) super.setFields(fields);
      }

      @Override
      public MessageUser setKey(java.lang.String key) {
        return (MessageUser) super.setKey(key);
      }

      @Override
      public MessageUser setOauthToken(java.lang.String oauthToken) {
        return (MessageUser) super.setOauthToken(oauthToken);
      }

      @Override
      public MessageUser setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (MessageUser) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public MessageUser setQuotaUser(java.lang.String quotaUser) {
        return (MessageUser) super.setQuotaUser(quotaUser);
      }

      @Override
      public MessageUser setUserIp(java.lang.String userIp) {
        return (MessageUser) super.setUserIp(userIp);
      }

      @Override
      public MessageUser set(String parameterName, Object value) {
        return (MessageUser) super.set(parameterName, value);
      }
    }

  }

  /**
   * Create a request for the method "message".
   *
   * This request holds the parameters needed by the usermessaging server.  After setting any optional
   * parameters, call the {@link Message#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.andresrcb.gcmbackend.usermessaging.model.MessageRecord}
   * @return the request
   */
  public Message message(com.andresrcb.gcmbackend.usermessaging.model.MessageRecord content) throws java.io.IOException {
    Message result = new Message(content);
    initialize(result);
    return result;
  }

  public class Message extends UsermessagingRequest<com.andresrcb.gcmbackend.usermessaging.model.MessageRecord> {

    private static final String REST_PATH = "messageNotification";

    /**
     * Create a request for the method "message".
     *
     * This request holds the parameters needed by the the usermessaging server.  After setting any
     * optional parameters, call the {@link Message#execute()} method to invoke the remote operation.
     * <p> {@link
     * Message#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.andresrcb.gcmbackend.usermessaging.model.MessageRecord}
     * @since 1.13
     */
    protected Message(com.andresrcb.gcmbackend.usermessaging.model.MessageRecord content) {
      super(Usermessaging.this, "POST", REST_PATH, content, com.andresrcb.gcmbackend.usermessaging.model.MessageRecord.class);
    }

    @Override
    public Message setAlt(java.lang.String alt) {
      return (Message) super.setAlt(alt);
    }

    @Override
    public Message setFields(java.lang.String fields) {
      return (Message) super.setFields(fields);
    }

    @Override
    public Message setKey(java.lang.String key) {
      return (Message) super.setKey(key);
    }

    @Override
    public Message setOauthToken(java.lang.String oauthToken) {
      return (Message) super.setOauthToken(oauthToken);
    }

    @Override
    public Message setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Message) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Message setQuotaUser(java.lang.String quotaUser) {
      return (Message) super.setQuotaUser(quotaUser);
    }

    @Override
    public Message setUserIp(java.lang.String userIp) {
      return (Message) super.setUserIp(userIp);
    }

    @Override
    public Message set(String parameterName, Object value) {
      return (Message) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Usermessaging}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Usermessaging}. */
    @Override
    public Usermessaging build() {
      return new Usermessaging(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link UsermessagingRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setUsermessagingRequestInitializer(
        UsermessagingRequestInitializer usermessagingRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(usermessagingRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
