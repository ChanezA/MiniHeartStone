import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import $ from 'jquery';
import {HEROES} from './mock-heroes';
import { Hero } from './heroes/hero';
import {forEach} from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private serverUrl = 'http://localhost:8080/socket'
  private title = 'WebSockets chat';
  private stompClient;

  constructor(){
    this.initializeWebSocketConnection();
  }

  ngOnInit() {
    setTimeout(
      () => {
        this.sendMessage("waw");
      }, 500
    );
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    console.log(" je suis NBNB?B 1");
    this.stompClient.connect({}, function(frame) {
      console.log(" je suis NBNB?B 2");
      that.stompClient.subscribe("/chat", (message) => {
        console.log(" je suis NBNB?B 3");
        console.log(JSON.parse(message.body));
        if (message.body) {
          console.log(" je suis NBNB?B 4");
          var lel: 0;
          lel = JSON.parse(message.body).length;
          for (let i = 0; i < lel; i++) {
            console.log(" je suis NBNB?B 5");
            var hr: Hero;
            hr = { name: JSON.parse(message.body)[i] , pseudo: 'pseudo', difficulty: 1};
            HEROES[i] = hr;
          }
        }
      });
    });
  }

  sendMessage(message){
    this.stompClient.send("/app/send/message" , {},message);
    //$('#input').val('');
  }

}
