package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 24/11/2017.
 */

public class CustomItem {
    private String textIcon;
    private Integer icon;
    /*public CustomItem(Integer icon, String textIcon){
        this.icon=icon;
        this.textIcon=textIcon;
    }*/

//    private String rating;

    /*public CustomItem(String item1, String item2, String item3, String item4) {
        this.dataSet = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }*/

    public Integer getIcon() {
        return icon;
    }
    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTextIcon() {
        return textIcon;
    }
    public void setTextIcon(String textIcon) {
        this.textIcon = textIcon;
    }


}
