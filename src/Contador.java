/*******************************************************************************
 * Copyright 2012 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright 2012 Diego Martin Moreno (dmartmorsoft@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * <p>Title: Contador</p>
 * <p>Description: Muestra los datos de descargas y otros para ver debug de la aplicación</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 0.01
 */
public class Contador
    extends Canvas
    implements CommandListener {

    //Midlet para llamar a los mapas
  MIDlet midlet;

  //pantalla donde se muestra el menu
  Display display;

  //Comando salir de la aplicación
  static final Command salir = new Command("salir", Command.EXIT, 1);

  /**
   * Captura de comandos obligatorio para Canvas normal
   */
  public void commandAction(Command c, Displayable d) {
    ( (ClienteHTTP) midlet).pantallaMenu();
  }

  /**
   * Eventos de teclado
   */
  protected void keyPressed(int keyCode) {
    ( (ClienteHTTP) midlet).pantallaMenu();
  }


  public void paint(Graphics g) {
   g.setStrokeStyle(Graphics.SOLID);

   //blanco
   g.setColor(255, 255, 255);
   g.fillRect(0, 0, getHeight(), getWidth());
   //negro
   g.setColor(0, 0, 0);
   g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_PLAIN,Font.SIZE_SMALL));
   g.drawString("Kylobytes:"+Propiedades.getContador(), 5, 15, Graphics.LEFT | Graphics.TOP);
   g.drawString("Coste:"+(Propiedades.getContador()*2)+"centimos", 5, 35, Graphics.LEFT | Graphics.TOP);
   g.drawString("Descargas:"+Propiedades.getNumDescargas(), 5, 55, Graphics.LEFT | Graphics.TOP);
   g.drawString("Media:"+Propiedades.getTiempoDescarga()/Propiedades.getNumDescargas(), 5, 75, Graphics.LEFT | Graphics.TOP);
  }

  /**
   * Constructor del menu
   * @param d Display para mostrar el menu
   * @param m Midlet para llamar a otras clases
   */
  public Contador(MIDlet m) {
    midlet = m;
  }

 }