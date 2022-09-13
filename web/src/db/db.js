import Dexie from 'dexie';

export const db = new Dexie('project-pear');
db.version(1).stores({
    oldForms: '++id, type, name, finishedAt', // Primary key and indexed props
    completedReviews: '++id, finishedAt'
});