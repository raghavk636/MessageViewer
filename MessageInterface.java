public interface MessageInterface {

    //METHODS

    String getContent();
    void setContent(String content);
    MessageViewerUser getSender();
    void setSender(MessageViewerUser sender);
    MessageViewerUser getReceiver();
    void setReceiver(MessageViewerUser receiver);
} 
