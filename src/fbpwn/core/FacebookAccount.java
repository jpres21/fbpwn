/*
 * FBPwn
 * 
 * http://sourceforge.net/projects/fbpwn/
 * 
 * Copyright (C) 2011 - FBPwn
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fbpwn.core;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Represents the a Facebook account
 */
public class FacebookAccount {

    private String profilePageURL; //URL of the profile page
    private WebClient webBrowser; // WebClient which connects to the facebook profile
    private String name;
    private String profilePhotoURL;
    private ImageIcon profilePicture;

    /**
     *  Creates a new Facebook account.     
     * @param profileURL Profile page of this account
     * @param Browser The web browser used to authenticate or send friend request to this Account
     */
    public FacebookAccount(String profileURL, WebClient Browser) {
        profilePageURL = profileURL;
        webBrowser = Browser;
    }

    /**
     * Gets the URL of the info page
     * @return The URL of the info page for this account
     */
    public String getInfoPageURL() {
        String infoPage;
        if (!profilePageURL.contains("id")) {
            infoPage = (profilePageURL.contains("?")) ? profilePageURL.substring(0, profilePageURL.indexOf("?") + 1) : profilePageURL + "?";
        } else {
            infoPage = profilePageURL + "&";
        }
        infoPage += "sk=info";
        return infoPage;
    }

    /**
     * Gets the URL of the tagged photos page
     * @return A String representing the URL of the tagged photos page
     */
    public String getTaggedPhotosURL() {
        String taggedPhotosPage;
        if (!profilePageURL.contains("id")) {
            taggedPhotosPage = (profilePageURL.contains("?")) ? profilePageURL.substring(0, profilePageURL.indexOf("?") + 1) : profilePageURL + "?";
        } else {
            taggedPhotosPage = profilePageURL + "&";
        }
        taggedPhotosPage += "sk=photos";
        return taggedPhotosPage;
    }

    /**
     * Gets the URL for the albums' pages
     * @return List of URLs one for each album page
     * @throws IOException If failed to reach Facebook.com
     */
    public ArrayList<String> getAlbumsURLs() throws IOException {
        ArrayList<String> albumsURLs = new ArrayList<String>();
        //parsing Tagged photos page to extract Albums Hrefs
        List<HtmlAnchor> Hrfs = ((HtmlPage) getWebBrowser().getPage(getTaggedPhotosURL())).getAnchors();
        for (int i = 0; i < Hrfs.size(); i++) {
            if (Hrfs.get(i).getHrefAttribute().contains("media/set") && !albumsURLs.contains(Hrfs.get(i).getHrefAttribute())) {
                albumsURLs.add(Hrfs.get(i).getHrefAttribute());
            }
        }
        albumsURLs.remove(0);
        return albumsURLs;
    }

    /**
     * Gets the web browser used to Authenticate on this Account or send friend request to this Account
     * @return The web browser
     */
    public WebClient getBrowser() {
        return getWebBrowser();
    }

    /**
     * Gets the FacebookAccount's profile page
     * @return The FacebookAccount's profile page
     */
    public String getProfilePageURL() {
        return profilePageURL;
    }

    public String getMobileInfoPageURL() {
        String mobInfo = profilePageURL;

        int indexOfparams = mobInfo.indexOf('&');
        if (indexOfparams == -1) {
            mobInfo = "http://m." + mobInfo.substring(mobInfo.indexOf("facebook"));
        } else {
            mobInfo = "http://m." + mobInfo.substring(mobInfo.indexOf("facebook"), indexOfparams);
        }
        if (mobInfo.contains("id")) {
            return mobInfo + "&v=info";
        } else {
            return mobInfo + "?v=info";
        }
    }

    public String getMobileFriendsPageUrl() {
        try {
            HtmlPage profilePage = webBrowser.getPage(getMobileInfoPageURL());
            List<HtmlAnchor> anchs = profilePage.getAnchors();
            for (int i = 0; i < anchs.size(); i++) {
                if (anchs.get(i).getHrefAttribute().contains("friends.php") && anchs.get(i).getAttribute("class").equals("")) {
                    return ("http://m.facebook.com" + anchs.get(i).getHrefAttribute());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FacebookAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailingHttpStatusCodeException ex) {
            Logger.getLogger(FacebookAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the FacebookAccount's friend page
     * @return The FacebookAccount's friend page
     */
    public String getFriendsPageURL() {
        String friendsPage;
        if (!profilePageURL.contains("id")) {
            friendsPage = (profilePageURL.contains("?")) ? profilePageURL.substring(0, profilePageURL.indexOf("?") + 1) : profilePageURL + "?";
        } else {
            friendsPage = profilePageURL + "&";
        }
        friendsPage += "sk=friends";
        return friendsPage;
    }

    /**
     * Gets a list of friends for this account.     
     * @return The list of friends
     * @throws IOException If failed to reach Facebook.com
     */
    public ArrayList<FacebookAccount> getFriends() throws IOException {

        ArrayList<FacebookAccount> friendsList = new ArrayList<FacebookAccount>();


        String page = getMobileFriendsPageUrl();

        if (page == null) {
            return null;
        }

        HtmlPage friendsPage = getBrowser().getPage(page);
        while (true) {
            HtmlElement friends = friendsPage.getElementById("rootContent");
            int index = 0;

            DomNodeList<HtmlElement> images = friends.getElementsByTagName("img");
            DomNodeList<HtmlElement> hrefs = friends.getElementsByTagName("a");
            ArrayList<HtmlElement> validHrefs = new ArrayList<HtmlElement>();

            for (HtmlElement href : hrefs) {
                HtmlAnchor anc = (HtmlAnchor) href;
                if (anc.hasAttribute("name") == true
                        && anc.hasAttribute("href") == true) {
                    validHrefs.add(href);
                }
            }

            for (int i = 0; i < validHrefs.size(); i++) {

                FacebookAccount newAccount = new FacebookAccount("http://facebook.com" + validHrefs.get(i).getAttribute("href"), webBrowser);
                newAccount.setProfilePhotoURL(images.get(i).getAttribute("src"));
                newAccount.setName(validHrefs.get(i).getAttribute("name"));
                friendsList.add(newAccount);
            }
            try {
                friendsPage = friendsPage.getAnchorByText("See More Friends").click();
            } catch (Exception ex) {
                break;
            }
        }
        
        if(friendsList.size() == 0 ) {
            return null;
        }
        
        return friendsList;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the photoURL
     */
    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    /**
     * @param photoURL the photoURL to set
     */
    public void setProfilePhotoURL(String photoURL) {
        this.profilePhotoURL = photoURL.replace("_q", "_n").replace("s50x50/", "");
    }

    /**
     * @return the webBrowser
     */
    public WebClient getWebBrowser() {
        return webBrowser;
    }

    /**
     * @param webBrowser the webBrowser to set
     */
    public void setWebBrowser(WebClient webBrowser) {
        this.webBrowser = webBrowser;
    }

    /**
     * @param profilePageURL the profilePageURL to set
     */
    public void setProfilePageURL(String profilePageURL) {
        this.profilePageURL = profilePageURL;
    }

    /**
     * @return the profilePicture
     */
    public ImageIcon getProfilePicture() {
        return profilePicture;
    }

    /**
     * @param profilePicture the profilePicture to set
     */
    public void setProfilePicture(ImageIcon profilePicture) {
        this.profilePicture = profilePicture;
    }
}