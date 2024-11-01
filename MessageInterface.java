public interface MessageInterface {
    public String getContent();
    public void getContent(String content);
    public SocialMediaUser getSender();
    public void getSender(String sender);
    public SocialMediaUser getReceiver();
    public void getReceiver(String receiver);
}
