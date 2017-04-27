## Data
Add your data as csv in *data/rating.csv* folder:

user_id,item_it,rating

## Generating the model:
You can generate the model using running *UserRating* 
a model file should be saved in *data/als-model*

## Prediction/Recommendation:
Running *PredictUserRating* should load the model and ask for  
options to predict (p) or recommend (r).

### Prediction:
Input: Need to provide user-id and item-id

Output: Prediction for user-id for the item-id

### Recommendation:
Input: Need to provide user-id

Output: Would return the best 5 items for user-id
