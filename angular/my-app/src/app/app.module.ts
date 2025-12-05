import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { OrderComponent } from './order/order.component';
import { SinhvienComponent } from './sinhvien/sinhvien.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { ViewlistsvComponent } from './sinhvien2/viewlistsv/viewlistsv.component';
import { ViewdetailsvComponent } from './sinhvien2/viewdetailsv/viewdetailsv.component';
import { ViewmhbysvidComponent } from './sinhvien2/viewmhbysvid/viewmhbysvid.component';
import { ViewgradebyidComponent } from './sinhvien2/viewgradebyid/viewgradebyid.component';
import { InputscoreComponent } from './sinhvien2/inputscore/inputscore.component';
import { CheckpassComponent } from './sinhvien2/checkpass/checkpass.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    OrderComponent,
    SinhvienComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    ViewlistsvComponent,
    ViewdetailsvComponent,
    ViewmhbysvidComponent,
    ViewgradebyidComponent,
    InputscoreComponent,
    CheckpassComponent
  ],
  imports: [
    BrowserModule
    , HttpClientModule,
     AppRoutingModule,
     MaterialModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [HomeComponent]
})
export class AppModule { }
