import domain from 'app/entities/domain/domain.reducer';
import scenario from 'app/entities/scenario/scenario.reducer';
import module from 'app/entities/module/module.reducer';
import learner from 'app/entities/learner/learner.reducer';
import educator from 'app/entities/educator/educator.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  domain,
  scenario,
  module,
  learner,
  educator,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
