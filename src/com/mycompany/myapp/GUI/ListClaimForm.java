/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceReclamation;
import com.mycompany.myapp.services.ServiceUser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author ghofrane
 */
public class ListClaimForm extends ProfilForm{
Form current;
    public ListClaimForm() {
      current =this;
           setTitle("Reports");
           Container gl = new Container(BoxLayout.y());
           super.addSideMenu();
           readClaim(12,gl);
          /* Button btnAddClaim = new Button();
    //       btnAddClaim.addActionListener(e-> new AddClaimForum(current).show());
           Style closeStyle = btnAddClaim.getAllStyles();
           closeStyle.setFgColor(0xffffff);
           closeStyle.setBgTransparency(0);
           closeStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
           closeStyle.setPadding(3, 3, 3, 3);
           closeStyle.setBorder(RoundBorder.create().shadowOpacity(100));
           FontImage.setMaterialIcon(btnAddClaim, FontImage.MATERIAL_ADD);
           Container btn = new Container(BoxLayout.xRight());
         //  btn.add(btnAddClaim);
           gl.add(btn);*/
           add(gl);
         
       
    }
    private void readClaim(int uid,Container gl){
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
         Container listclaim = new Container(BoxLayout.y()); 
         listclaim.getAllStyles().setMarginTop(45);
        
          ServiceReclamation ff = new ServiceReclamation();
          ArrayList<Reclamation> claims = ff.getAllClaims(uid);
         for (Reclamation r : claims)
                {
                  Container c = new Container(BoxLayout.y()){
            @Override
            protected Dimension calcPreferredSize() {
                Dimension d = super.calcPreferredSize(); 
                d.setWidth(CN.getDisplayWidth()*93/100);
                return d;
            } 
                  };
                  Container c1 = new Container(BoxLayout.y());
                  Container c2 = new Container(BoxLayout.x());
                  Style tftStyle =  c.getAllStyles();
                Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
                 tftStyle.setBorder(RoundRectBorder.create().
                         strokeColor(0xffffff).
                         strokeOpacity(120).
                         stroke(borderStroke));
                         tftStyle.setBgColor(0xffffff);
          tftStyle.setBgTransparency(255);
          tftStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
          tftStyle.setMargin(Component.BOTTOM, 3);
          tftStyle.setMarginLeft(2);
                  Label cont = new Label( r.getContenu());
                  Label date  = new Label(formatter.format(r.getDate()));
                
                  SpanLabel sp = new SpanLabel(); 
                  SpanLabel spt = new SpanLabel(); 
                 
                  Button delete = new Button(FontImage.MATERIAL_DELETE);  
                  final int id = r.getId();        
                
                   Container c3 = new Container(BoxLayout.x());
                   ImageViewer imgv = new ImageViewer();  
                  imgv.setImage(setImage(ServiceUser.getInstance().getAUser(r.getReclamer()).get(0).getId()));
                  sp.setText(ServiceUser.getInstance().getAUser(r.getReclamer()).get(0).getUsername());
                  c3.add(imgv);
                  c3.add(sp);
                  c1.add(date);
                  c1.add(cont);
                  c2.add(delete);
                  c.add(c3);
                  c.add(c1);
                  c.add(c2);
                  listclaim.add(c);
               
                  delete.addActionListener((ActionListener)(ActionEvent evt1) -> {
                      ff.deleteClaim(id);
                        new ListClaimForm().show();

                  });
                }
               
                gl.add(listclaim);
               
       
        
    }
    public URLImage setImage(int id){
        User u = new User();
        ServiceUser su = new ServiceUser();
        String img =su.getAUser(id).get(0).getPhoto();
         EncodedImage imgs = EncodedImage.createFromImage(Image.createImage(150,150), true);
                        URLImage imgg= URLImage.createToStorage(imgs,img, "http://localhost/pidev/web/images/"+img);
                      return imgg;
                       
                        
    }
}