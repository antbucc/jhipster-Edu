import domain from 'app/entities/domain/domain.reducer';
import scenario from 'app/entities/scenario/scenario.reducer';
import module from 'app/entities/module/module.reducer';
import learner from 'app/entities/learner/learner.reducer';
import educator from 'app/entities/educator/educator.reducer';
import competence from 'app/entities/competence/competence.reducer';
import concept from 'app/entities/concept/concept.reducer';
import activity from 'app/entities/activity/activity.reducer';
import fragment from 'app/entities/fragment/fragment.reducer';
import precondition from 'app/entities/precondition/precondition.reducer';
import effect from 'app/entities/effect/effect.reducer';
import condition from 'app/entities/condition/condition.reducer';
import path from 'app/entities/path/path.reducer';
import goal from 'app/entities/goal/goal.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  domain,
  scenario,
  module,
  learner,
  educator,
  competence,
  concept,
  activity,
  fragment,
  precondition,
  effect,
  condition,
  path,
  goal,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
