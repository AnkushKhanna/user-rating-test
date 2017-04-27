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


### Sample Example:
#### Prediction:
[Out] Enter p for prediction, r for recommendation, anything else to exit.

[In] p


[Out] Enter user id [Int]

[In] 2


[Out] Enter item id [Int]

[In] 51360


[Out] Prediction is 5.675551598366361

#### Recommendation:
[In]Enter p for prediction, r for recommendation, anything else to exit.

[Out] r


[In] Enter user id [Int]

[Out] 469


[Out] Recommendation is (39002,6.3528605363038935) - (1496,6.3528605363038935) - (53879,6.1937178585782675) - (70588,6.113054081350597) - (27681,6.113054081350597)

