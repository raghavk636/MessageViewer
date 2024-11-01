public interface MessageInterface {
    String getContent();
    void getContent(String content);
    SocialMediaUser getSender();
    void getSender(String sender);
    SocialMediaUser getReciver();
    void getReceiver(String receiver);
}
