import { Routes } from '@angular/router';

const routes: Routes = [
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
  {
    path: 'departments',
    loadChildren: () => import('./department/department.routes'),
    title: 'inotificareApp.department.home.title',
  },
  {
    path: 'my-departments',
    loadChildren: () => import('./department/department.routes'),
    title: 'inotificareApp.department.home.title',
  },
];

export default routes;
