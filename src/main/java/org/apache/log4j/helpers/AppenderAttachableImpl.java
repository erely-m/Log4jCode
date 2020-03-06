/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j.helpers;

import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.LoggingEvent;

import org.apache.log4j.Appender;
import java.util.Vector;
import java.util.Enumeration;

/**
   A straightforward implementation of the {@link AppenderAttachable}
   interface.

   @author Ceki G&uuml;lc&uuml;
   @since version 0.9.1 */
public class AppenderAttachableImpl implements AppenderAttachable { //可使用的所有Appender
  
  /** Array of appenders. */
  protected Vector  appenderList; //Appender集合

  /**
     Attach an appender. If the appender is already in the list in
     won't be added again.
  */
  public
  void addAppender(Appender newAppender) { //添加Appender
    // Null values for newAppender parameter are strictly forbidden.
    if(newAppender == null)
      return;

    if(appenderList == null) { //如果appenderList为空初始化
      appenderList = new Vector(1);
    }
    if(!appenderList.contains(newAppender)) //如果不包含添加
      appenderList.addElement(newAppender);
  }

  /**
     Call the <code>doAppend</code> method on all attached appenders.  */
  public
  int appendLoopOnAppenders(LoggingEvent event) { //循环Appender
    int size = 0;
    Appender appender;

    if(appenderList != null) {
      size = appenderList.size();
      for(int i = 0; i < size; i++) {
	appender = (Appender) appenderList.elementAt(i); //获取所有Appender
	appender.doAppend(event);  //执行输出
      }
    }    
    return size; //返回appender的个数
  }


  /**
     Get all attached appenders as an Enumeration. If there are no
     attached appenders <code>null</code> is returned.
     
     @return Enumeration An enumeration of attached appenders.
   */
  public
  Enumeration getAllAppenders() { //获取所有Appender
    if(appenderList == null)
      return null;
    else 
      return appenderList.elements();    
  }

  /**
     Look for an attached appender named as <code>name</code>.

     <p>Return the appender with that name if in the list. Return null
     otherwise.  
     
   */
  public
  Appender getAppender(String name) { //根据名称获取Appender
     if(appenderList == null || name == null)
      return null;

     int size = appenderList.size();
     Appender appender;
     for(int i = 0; i < size; i++) {
       appender = (Appender) appenderList.elementAt(i);
       if(name.equals(appender.getName()))
	  return appender;
     }
     return null;    
  }


  /**
     Returns <code>true</code> if the specified appender is in the
     list of attached appenders, <code>false</code> otherwise.

     @since 1.2 */
  public 
  boolean isAttached(Appender appender) { //判断Appender是否在集合中
    if(appenderList == null || appender == null)
      return false;

     int size = appenderList.size();
     Appender a;
     for(int i = 0; i < size; i++) {
       a  = (Appender) appenderList.elementAt(i);
       if(a == appender)
	  return true;
     }
     return false;    
  }



  /**
   * Remove and close all previously attached appenders.
   * */
  public
  void removeAllAppenders() { //移除所有的Appender
    if(appenderList != null) {
      int len = appenderList.size();      
      for(int i = 0; i < len; i++) {
	Appender a = (Appender) appenderList.elementAt(i);
	a.close();
      }
      appenderList.removeAllElements();
      appenderList = null;      
    }
  }


  /**
     Remove the appender passed as parameter form the list of attached
     appenders.  */
  public
  void removeAppender(Appender appender) { //根据Appender移除Appdener
    if(appender == null || appenderList == null) 
      return;
    appenderList.removeElement(appender);    
  }


 /**
    Remove the appender with the name passed as parameter form the
    list of appenders.  
  */
  public
  void removeAppender(String name) { //根据名称移除Appender
    if(name == null || appenderList == null) return;
    int size = appenderList.size();
    for(int i = 0; i < size; i++) {
      if(name.equals(((Appender)appenderList.elementAt(i)).getName())) {
	 appenderList.removeElementAt(i);
	 break;
      }
    }
  }

}
