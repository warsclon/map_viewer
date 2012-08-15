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
 * <p>Title: Consola</p>
 * <p>Description: Consola para ver mensajes dentro de la aplicación</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class Consola
    implements CommandListener {

  //Midlet para llamar a los mapas
  MIDlet midlet;

  //pantalla donde se muestra el menu
  Display display;

  //Comando salir de la consola
  static final Command salir = new Command("salir", Command.EXIT, 1);

  // textbox
  TextBox input = null;

  /**
   * Camtura eventos de la consola
   * @param c Teclado
   * @param d Pantalla
   */
  public void commandAction(Command c, Displayable d) {
    ( (ClienteHTTP) midlet).pantallaMenu();
  }

  /**
   * Eventos de teclado
   * @param keyCode Tecla presionada
   */
  protected void keyPressed(int keyCode) {
    ( (ClienteHTTP) midlet).pantallaMenu();
  }

  /**
   * Constructor de la consola
   * @param m Midlet
   * @param display Pantalla
   */
  public Consola(MIDlet m, Display display) {
    midlet = m;
    input = new TextBox("Consola", "", 5000, TextField.ANY);
    input.addCommand(salir);
    input.setCommandListener(this);
    input.setString(Propiedades.getConsola());
    display.setCurrent(input);

  }

}