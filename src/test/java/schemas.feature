Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}

		* set schemas.nutrient =
		"""
		{
            id: '##number',
            name: '#string',
            unitName: '#string',
            amount: '#number'
		}
		"""

		* set schemas.food =
        		"""
        		{
                    id: '##number',
                    fdcId: '##number',
                    description: '#string',
                    brandOwner: '##string',
                    ingredients: '##string',
                    servingSize: '#number',
                    servingSizeUnit: '#string',
                    householdServingFullText: '##string',
                    nutrients: '##[] schemas.nutrient'
        		}
        		"""